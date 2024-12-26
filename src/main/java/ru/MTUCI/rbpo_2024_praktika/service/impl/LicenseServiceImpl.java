package ru.MTUCI.rbpo_2024_praktika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.MTUCI.rbpo_2024_praktika.controller.dto.LicenseActivationRequest;
import ru.MTUCI.rbpo_2024_praktika.controller.dto.LicenseCreationRequest;
import ru.MTUCI.rbpo_2024_praktika.controller.dto.LicenseInfoRequest;
import ru.MTUCI.rbpo_2024_praktika.model.*;
import ru.MTUCI.rbpo_2024_praktika.repository.*;
import ru.MTUCI.rbpo_2024_praktika.service.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

//TODO: 1. validateRenewal - лучше проверить пользователя у лицензии, иначе продлить её может кто угодно
//TODO: 2. generateRenewalTicket - это не создание тикета, смотрите задание. Нужно вернуть подписанный объект
//TODO: 3. generateTicket - см. TODO 2
//TODO: 4. Map<String, Object> - почему нельзя использовать DTO?
//TODO: 5. registerOrUpdateDevice - метод не соответствует имени. И устройства должны иметь уникальный mac-адрес
//TODO: 6. activateLicense - нужно проверять истекла ли лицензия и принадлежит ли она тому же пользователю, при повторной активации
//TODO: 7. activateLicense - не устанавливается дата окончания и пользователь при первой активации
//TODO: 8. activateLicense - должно проверяться максимальное число устройств лицензии, и активироваться, если есть доступное место
//TODO: 9. getLicenseInfo и activateLicense - отсутствует генерация тикета
//TODO: 10. createLicense - нельзя ставить максимальное число устройств равным 0
//TODO: 11. createLicense - дату окончания устанавливать при первой активации
//TODO: 12. activateLicense - не проверяется, что на устройстве уже активирована эта лицензия
//TODO: 13. В классе намешана логика, разделить её по сервисам в зависимости от сущности, с которой она работает

@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final LicenseTypeRepository licenseTypeRepository;
    private final DeviceLicenseRepository deviceLicenseRepository;
    private final DeviceService deviceService;
    private final DeviceLicenseService deviceLicenseService;
    private final TicketService ticketService;
    private final LicenseHistoryService licenseHistoryService;

    @Override
    @Transactional
    public License createLicense(Long productId, Long ownerId, Long licenseTypeId, LicenseCreationRequest licenseCreationRequest) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        User owner = userRepository.findById(ownerId).orElseThrow(() -> new IllegalArgumentException("User (owner) not found with id: " + ownerId));

        LicenseType licenseType = licenseTypeRepository.findById(licenseTypeId).orElseThrow(() -> new IllegalArgumentException("License type not found with id: " + licenseTypeId));

        License license = new License();

        license.setCode(UUID.randomUUID().toString());

        license.setProduct(product);
        license.setOwner(owner);
        license.setLicenseType(licenseType);

        if (licenseCreationRequest.getDevicesCount() <= 0) {
            throw new IllegalArgumentException("DevicesCount must be greater than 0");
        }
        license.setDevicesCount(licenseCreationRequest.getDevicesCount());
        license.setDescription(licenseCreationRequest.getDescription());
        license.setDuration(licenseType.getDefaultDuration());
        license.setBlocked(false);

        License savedLicense = licenseRepository.save(license);

        licenseHistoryService.createLicenseHistory(savedLicense, owner, "Created", "New License Created");

        return savedLicense;
    }

    @Override
    @Transactional
    public Ticket activateLicense(String activationCode, LicenseActivationRequest licenseActivationRequest, User user) {

        Device device = deviceService.registerOrUpdateDevice(licenseActivationRequest, user);

        License license = licenseRepository.findByCode(activationCode).orElse(null);
        if (license == null) {
            throw new IllegalArgumentException("License not found with code: " + activationCode);
        }

        validateActivation(license, device, user);

        deviceLicenseService.createDeviceLicense(license, device);

        if (license.getFirst_activation_date() == null) {
            LocalDate endDate = LocalDate.now().plusDays(license.getDuration());
            license.setEnding_date(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            license.setFirst_activation_date(new Date());
        }

        license.setUser(user);

        licenseHistoryService.createLicenseHistory(license, user, "Activated", "License Activated for user with id " + user.getId() + " and device with id " + device.getId());

        Ticket ticket = ticketService.generateTicket(license, device);

        return ticket;
    }

    private void validateActivation(License license, Device device, User user) {
        if(license.getBlocked()){
            throw new IllegalArgumentException("License is blocked: " + license.getId());
        }
        if (!license.getOwner().getId().equals(user.getId())) {
            throw new IllegalArgumentException("License does not belong to this user: " + user.getId());
        }
        if (license.getEnding_date() != null && license.getEnding_date().before(new Date())) {
            throw new IllegalArgumentException("License has expired: " + license.getId());
        }
        if(license.getDevicesCount() > 0 && deviceLicenseRepository.countByLicenseId(license.getId()) >= license.getDevicesCount() ) {
            throw new IllegalArgumentException("License limit of devices exceeded: " + license.getId());
        }
    }

    @Override
    @Transactional
    public Ticket renewLicense(String licenseCode, User user) {
        License license = licenseRepository.findByCode(licenseCode).orElseThrow(() ->
                new IllegalArgumentException("License not found with code: " + licenseCode));

        validateRenewal(license, user);

        int duration = license.getDuration();
        LocalDate endingDate = LocalDate.now().plusDays(duration);
        license.setEnding_date(Date.from(endingDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        licenseRepository.save(license);

        Ticket ticket = ticketService.generateRenewalTicket(license);

        licenseHistoryService.createLicenseHistory(license, user, "Renewed", "License renewed");

        return ticket;
    }

    private void validateRenewal(License license, User user) {
        if (license.getBlocked() != null && license.getBlocked()) {
            throw new IllegalArgumentException("License is blocked");
        }
        if (license.getEnding_date().before(new Date())) {
            throw new IllegalArgumentException("License has expired");
        }
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
    public List<Ticket> getLicenseInfo(LicenseInfoRequest licenseInfoRequest, User user) {
        Device device = deviceService.findDeviceByInfo(licenseInfoRequest.getMac(),licenseInfoRequest.getName(), user);
        if (device == null) {
            throw new IllegalArgumentException("Device not found");
        }

        List<License> licenses = getActiveLicensesForDevice(device, user);
        List<Ticket> tickets = new ArrayList<>();

        for(License license : licenses){
            tickets.add(ticketService.generateTicket(license, device));
        }
        return tickets;
    }

    private List<License> getActiveLicensesForDevice(Device device, User user) {
        return deviceLicenseRepository.findByDeviceId(device.getId()).stream()
                .map(DeviceLicense::getLicense)
                .collect(Collectors.toList());
    }

    @Override
    public List<License> findAllLicenses() {
        return licenseRepository.findAll();
    }

    @Override
    public List<License> findByOwnerId(Long id) {
        return licenseRepository.findByOwnerId(id);
    }

    @Override
    public List<License> findByProductId(Long id) {
        return licenseRepository.findByProductId(id);
    }


    @Override
    public License findLicenseById(Long id) {
        return licenseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid license id: " + id));
    }

    @Override
    public void deleteLicenseById(Long id) {
        licenseRepository.deleteById(id);
    }
}