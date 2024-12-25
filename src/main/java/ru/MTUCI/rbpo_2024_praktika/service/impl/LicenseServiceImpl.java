package ru.MTUCI.rbpo_2024_praktika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.MTUCI.rbpo_2024_praktika.model.*;
import ru.MTUCI.rbpo_2024_praktika.repository.*;
import ru.MTUCI.rbpo_2024_praktika.service.LicenseService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final LicenseTypeRepository licenseTypeRepository;
    private final LicenseHistoryRepository licenseHistoryRepository;
    private final DeviceLicenseRepository deviceLicenseRepository;
    private final DeviceRepository deviceRepository;


    @Override
    @Transactional
    public String renewLicense(String licenseCode, User user) {
        License license = licenseRepository.findByCode(licenseCode).orElse(null);
        if (license == null) {
            throw new IllegalArgumentException("Invalid license code: " + licenseCode);
        }
        validateRenewal(license, user);

        LocalDate expirationDate = LocalDate.now().plusDays(license.getDuration());
        license.setEnding_date(Date.from(expirationDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        licenseRepository.save(license);

        recordLicenseChange(license, user, "Renewed", "License renewed");

        return generateRenewalTicket(license);
    }

    private String generateRenewalTicket(License license) {
        return "License renewed, new ending date is: " + license.getEnding_date();
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
    @Transactional
    public License activateLicense(String activationCode, Map<String, Object> params, User user) {
        License license = licenseRepository.findByCode(activationCode).orElse(null);
        if (license == null) {
            throw new IllegalArgumentException("License not found with code: " + activationCode);
        }

        Device device = registerOrUpdateDevice(params, user);

        validateActivation(license, device, user);

        createDeviceLicense(license, device);

        if (license.getFirst_activation_date() == null) {
            license.setFirst_activation_date(new Date());
            licenseRepository.save(license);
        }

        license.setUser(user);

        recordLicenseChange(license, user, "Activated", "License Activated for user with id " + user.getId() + " and device with id " + device.getId());

        String ticket = generateTicket(license, device);

        return license;
    }


    @Override
    public String getLicenseInfo(Map<String, Object> deviceInfo, User user) {
        Device device = findDeviceByInfo(deviceInfo, user);

        if (device == null) {
            throw new IllegalArgumentException("Device not found");
        }

        List<License> licenses = getActiveLicensesForDevice(device, user);

        return generateTicket(licenses, device);
    }

    private String generateTicket(List<License> licenses, Device device) {
        return licenses.stream()
                .map(license -> "License: " + license.getId() + " Device: " + device.getId())
                .collect(Collectors.joining(", "));
    }

    private List<License> getActiveLicensesForDevice(Device device, User user) {
        return deviceLicenseRepository.findByDeviceId(device.getId()).stream()
                .map(DeviceLicense::getLicense)
                .collect(Collectors.toList());
    }

    private Device findDeviceByInfo(Map<String, Object> deviceInfo, User user) {
        Device device = null;
        if (deviceInfo.containsKey("deviceId")) {
            Long deviceId = Long.parseLong(deviceInfo.get("deviceId").toString());
            device = deviceRepository.findById(deviceId).orElse(null);

        }

        return device;
    }


    private String generateTicket(License license, Device device) {
        return "Ticket for License: " + license.getId() + " Device: " + device.getId();
    }

    private void recordLicenseChange(License license, User user, String action, String description) {
        LicenseHistory licenseHistory = new LicenseHistory();
        licenseHistory.setLicense(license);
        licenseHistory.setDescription(description);
        licenseHistory.setStatus(action);
        licenseHistory.setUser(user);
        licenseHistory.setChangeDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        licenseHistoryRepository.save(licenseHistory);
    }


    private void createDeviceLicense(License license, Device device) {
        if (license.getDevicesCount() != null && license.getDeviceLicenses().size() >= license.getDevicesCount()) {
            throw new IllegalArgumentException("Device limit reached for license with id " + license.getId());
        }
        if (deviceLicenseRepository.findByDeviceIdAndLicenseId(device.getId(), license.getId()).isPresent()) {
            throw new IllegalArgumentException("Device with id " + device.getId() + " already activated for license with id " + license.getId());
        }
        DeviceLicense deviceLicense = new DeviceLicense();
        deviceLicense.setDevice(device);
        deviceLicense.setLicense(license);
        deviceLicense.setActivation_date(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        deviceLicenseRepository.save(deviceLicense);
    }

    private void validateActivation(License license, Device device, User user) {
        if (device == null) {
            throw new IllegalArgumentException("Device not found");
        }

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
    }

    private Device registerOrUpdateDevice(Map<String, Object> params, User user) {
        Device device = null;
        if (params.containsKey("deviceId")) {
            Long deviceId = Long.parseLong(params.get("deviceId").toString());
            device = deviceRepository.findById(deviceId).orElse(null);

        }
        if (device == null) {
            throw new IllegalArgumentException("Device id is not passed");
        }

        return device;
    }

    @Override
    @Transactional
    public License createLicense(Long productId, Long ownerId, Long licenseTypeId, Map<String, Object> params) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new IllegalArgumentException("Product not found with id: " + productId);
        }

        User owner = userRepository.findById(ownerId).orElse(null);
        if (owner == null) {
            throw new IllegalArgumentException("User (owner) not found with id: " + ownerId);
        }

        LicenseType licenseType = licenseTypeRepository.findById(licenseTypeId).orElse(null);
        if (licenseType == null) {
            throw new IllegalArgumentException("License type not found with id: " + licenseTypeId);
        }

        License license = new License();

        license.setCode(UUID.randomUUID().toString());
        license.setProduct(product);
        license.setOwner(owner);
        license.setLicenseType(licenseType);
        license.setBlocked(false);

        if (params.containsKey("devicesCount")) {
            license.setDevicesCount(Integer.parseInt(params.get("devicesCount").toString()));
        } else {
            license.setDevicesCount(0);
        }
        if (params.containsKey("description")) {
            license.setDescription(params.get("description").toString());
        }

        license.setDuration(licenseType.getDefaultDuration());
        LocalDate creationDate = LocalDate.now();
        LocalDate expirationDate = creationDate.plusDays(licenseType.getDefaultDuration());
        license.setEnding_date(Date.from(expirationDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        License savedLicense = licenseRepository.save(license);

        recordLicenseChange(savedLicense, owner, "Created", "License Created");


        return savedLicense;
    }


    @Override
    public List<License> getAllLicenses() {
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
    public License updateLicense(License license) {
        return licenseRepository.save(license);
    }

    @Override
    public void deleteLicenseById(Long id) {
        licenseRepository.deleteById(id);
    }
}