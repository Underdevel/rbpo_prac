package ru.MTUCI.rbpo_2024_praktika.service;

import ru.MTUCI.rbpo_2024_praktika.model.License;
import ru.MTUCI.rbpo_2024_praktika.model.LicenseHistory;
import ru.MTUCI.rbpo_2024_praktika.model.User;

import java.util.List;
import java.util.Optional;

public interface LicenseHistoryService {
    List<LicenseHistory> findAllLicenseHistories();
    List<LicenseHistory> findAllLicenseHistoriesByLicenseId(Long licenseId);
    List<LicenseHistory> findAllLicenseHistoriesByUserId(Long userId);
    Optional<LicenseHistory> findLicenseHistoryById(Long id);

    void createLicenseHistory(License license, User user, String action, String description);

    //LicenseHistory updateLicenseHistory(LicenseHistory licenseHistory);
    //void deleteLicenseHistoryById(Long id);
}
