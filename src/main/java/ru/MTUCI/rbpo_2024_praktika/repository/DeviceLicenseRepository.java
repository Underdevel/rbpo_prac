package ru.MTUCI.rbpo_2024_praktika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.MTUCI.rbpo_2024_praktika.model.DeviceLicense;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceLicenseRepository extends JpaRepository<DeviceLicense, Long> {
    List<DeviceLicense> findAllByLicenseId(Long licenseId);
    List<DeviceLicense> findByDeviceId(Long deviceId);
    Optional<DeviceLicense> findByDeviceIdAndLicenseId(Long deviceId, Long licenseId);
    Integer countByLicenseId(Long licenseId);
}