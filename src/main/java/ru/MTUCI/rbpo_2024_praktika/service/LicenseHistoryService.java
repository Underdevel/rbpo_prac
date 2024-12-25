package ru.MTUCI.rbpo_2024_praktika.service;

import ru.MTUCI.rbpo_2024_praktika.model.LicenseHistory;

import java.util.List;
import java.util.Optional;

public interface LicenseHistoryService {
    List<LicenseHistory> getAllLicenseHistories();
    List<LicenseHistory> getAllLicenseHistoriesByLicenseId(Long licenseId);
    List<LicenseHistory> getAllLicenseHistoriesByUserId(Long userId);
    Optional<LicenseHistory> findLicenseHistoryById(Long id);

    LicenseHistory createLicenseHistory(LicenseHistory licenseHistory);
    LicenseHistory updateLicenseHistory(LicenseHistory licenseHistory);

    void deleteLicenseHistoryById(Long id);
}
