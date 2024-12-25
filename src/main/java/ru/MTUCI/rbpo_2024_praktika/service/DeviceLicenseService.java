package ru.MTUCI.rbpo_2024_praktika.service;

import ru.MTUCI.rbpo_2024_praktika.model.DeviceLicense;

import java.util.List;
import java.util.Optional;

public interface DeviceLicenseService {
    List<DeviceLicense> getAllDeviceLicenses();
    List<DeviceLicense> getAllDeviceLicensesByLicenseId(Long licenseId);
    List<DeviceLicense> getAllDeviceLicensesByDeviceId(Long deviceId);
    Optional<DeviceLicense> findDeviceLicenseById(Long id);

    DeviceLicense createDeviceLicense(DeviceLicense deviceLicense);
    DeviceLicense updateDeviceLicense(DeviceLicense deviceLicense);

    void deleteDeviceLicenseById(Long id);
}