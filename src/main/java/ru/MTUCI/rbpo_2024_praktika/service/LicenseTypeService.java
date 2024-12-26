package ru.MTUCI.rbpo_2024_praktika.service;

import ru.MTUCI.rbpo_2024_praktika.model.LicenseType;

import java.util.List;
import java.util.Optional;

public interface LicenseTypeService {
    List<LicenseType> findAllLicenseTypes();
    Optional<LicenseType> findLicenseTypeById(Long id);

    LicenseType createLicenseType(LicenseType licenseType);
    LicenseType updateLicenseType(LicenseType licenseType);

    void deleteLicenseTypeById(Long id);
}