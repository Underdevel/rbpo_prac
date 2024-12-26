package ru.MTUCI.rbpo_2024_praktika.service;

import ru.MTUCI.rbpo_2024_praktika.model.Device;
import ru.MTUCI.rbpo_2024_praktika.model.DeviceLicense;
import ru.MTUCI.rbpo_2024_praktika.model.License;

import java.util.List;
import java.util.Optional;

public interface DeviceLicenseService {
    List<DeviceLicense> findAllDeviceLicenses();
    List<DeviceLicense> findAllDeviceLicensesByLicenseId(Long licenseId);
    List<DeviceLicense> findAllDeviceLicensesByDeviceId(Long deviceId);
    Optional<DeviceLicense> findDeviceLicenseById(Long id);

    DeviceLicense createDeviceLicense(License license, Device device);
    DeviceLicense updateDeviceLicense(DeviceLicense deviceLicense);

    void deleteDeviceLicenseById(Long id);
}