package ru.MTUCI.rbpo_2024_praktika.service;

import ru.MTUCI.rbpo_2024_praktika.controller.dto.LicenseActivationRequest;
import ru.MTUCI.rbpo_2024_praktika.controller.dto.LicenseCreationRequest;
import ru.MTUCI.rbpo_2024_praktika.controller.dto.LicenseInfoRequest;
import ru.MTUCI.rbpo_2024_praktika.model.License;
import ru.MTUCI.rbpo_2024_praktika.model.Ticket;
import ru.MTUCI.rbpo_2024_praktika.model.User;

import java.util.List;

public interface LicenseService {
    License createLicense(Long productId, Long ownerId, Long licenseTypeId, LicenseCreationRequest licenseCreationRequest);
    Ticket activateLicense(String activationCode, LicenseActivationRequest licenseActivationRequest, User user);
    List<Ticket> getLicenseInfo(LicenseInfoRequest licenseInfoRequest, User user);
    Ticket renewLicense(String licenseCode, User user);
    List<License> findAllLicenses();
    List<License> findByOwnerId(Long id);
    List<License> findByProductId(Long id);
    License findLicenseById(Long id);
    void deleteLicenseById(Long id);
}