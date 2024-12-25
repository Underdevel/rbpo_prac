package ru.MTUCI.rbpo_2024_praktika.service;

import ru.MTUCI.rbpo_2024_praktika.model.License;
import ru.MTUCI.rbpo_2024_praktika.model.User;

import java.util.List;
import java.util.Map;

public interface LicenseService {
    License createLicense(Long productId, Long ownerId, Long licenseTypeId, Map<String, Object> params);
    License activateLicense(String activationCode, Map<String, Object> params, User user);
    String getLicenseInfo(Map<String, Object> deviceInfo, User user);
    String renewLicense(String licenseCode, User user);
    List<License> getAllLicenses();
    List<License> findByOwnerId(Long id);
    List<License> findByProductId(Long id);
    License findLicenseById(Long id);
    License updateLicense(License license);
    void deleteLicenseById(Long id);
}