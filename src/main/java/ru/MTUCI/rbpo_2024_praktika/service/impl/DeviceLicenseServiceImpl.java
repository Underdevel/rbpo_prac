package ru.MTUCI.rbpo_2024_praktika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.MTUCI.rbpo_2024_praktika.model.Device;
import ru.MTUCI.rbpo_2024_praktika.model.DeviceLicense;
import ru.MTUCI.rbpo_2024_praktika.model.License;
import ru.MTUCI.rbpo_2024_praktika.repository.DeviceLicenseRepository;
import ru.MTUCI.rbpo_2024_praktika.service.DeviceLicenseService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceLicenseServiceImpl implements DeviceLicenseService {

    private final DeviceLicenseRepository deviceLicenseRepository;

    @Override
    public Optional<DeviceLicense> findDeviceLicenseById(Long id) {
        return deviceLicenseRepository.findById(id);
    }

    @Override
    public List<DeviceLicense> findAllDeviceLicenses() {
        return deviceLicenseRepository.findAll();
    }

    @Override
    public DeviceLicense createDeviceLicense(License license, Device device) {
        if (license.getDevicesCount() != null && license.getDeviceLicenses().size() >= license.getDevicesCount()) {
            throw new IllegalArgumentException("Device limit reached for license with id " + license.getId());
        }
        if (deviceLicenseRepository.findByDeviceIdAndLicenseId(device.getId(), license.getId()).isPresent()) {
            throw new IllegalArgumentException("Device with id " + device.getId() + " already activated for license with id " + license.getId());
        }
        DeviceLicense deviceLicense = new DeviceLicense();
        deviceLicense.setLicense(license);
        deviceLicense.setDevice(device);
        deviceLicense.setActivation_date(new Date());
        return deviceLicenseRepository.save(deviceLicense);
    }

    @Override
    public DeviceLicense updateDeviceLicense(DeviceLicense deviceLicense) {
        if (!deviceLicenseRepository.existsById(deviceLicense.getId())){
            throw new IllegalStateException("Device license not found: " + deviceLicense.getId());
        }
        return deviceLicenseRepository.save(deviceLicense);
    }

    @Override
    public void deleteDeviceLicenseById(Long id) {
        if (!deviceLicenseRepository.existsById(id)){
            throw new IllegalStateException("Device license not found: " + id);
        }
        deviceLicenseRepository.deleteById(id);
    }


    @Override
    public List<DeviceLicense> findAllDeviceLicensesByLicenseId(Long licenseId) {
        return deviceLicenseRepository.findAllByLicenseId(licenseId);
    }

    @Override
    public List<DeviceLicense> findAllDeviceLicensesByDeviceId(Long deviceId) {
        return deviceLicenseRepository.findByDeviceId(deviceId);
    }
}