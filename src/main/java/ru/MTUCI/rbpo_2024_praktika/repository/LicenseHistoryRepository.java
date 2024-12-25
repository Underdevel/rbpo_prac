package ru.MTUCI.rbpo_2024_praktika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.MTUCI.rbpo_2024_praktika.model.LicenseHistory;

import java.util.List;

@Repository
public interface LicenseHistoryRepository extends JpaRepository<LicenseHistory, Long> {
    List<LicenseHistory> findAllByLicenseId(Long licenseId);
    List<LicenseHistory> findAllByUserId(Long userId);
}
