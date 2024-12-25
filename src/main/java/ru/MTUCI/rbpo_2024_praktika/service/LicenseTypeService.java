package ru.mtuci.rbpo_2024_praktika.service;

import ru.mtuci.rbpo_2024_praktika.model.LicenseType;

import java.util.List;
import java.util.Optional;

public interface LicenseTypeService {
    List<LicenseType> getAllLicenseTypes();
    Optional<LicenseType> findLicenseTypeById(Long id);

    LicenseType createLicenseType(LicenseType licenseType);
    LicenseType updateLicenseType(LicenseType licenseType);

    void deleteLicenseTypeById(Long id);
}