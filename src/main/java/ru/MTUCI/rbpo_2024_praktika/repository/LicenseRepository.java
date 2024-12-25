package ru.MTUCI.rbpo_2024_praktika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.MTUCI.rbpo_2024_praktika.model.License;

import java.util.List;
import java.util.Optional;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {
    Optional<License> findByCode(String code);
    List<License> findByOwnerId(Long ownerId);
    List<License> findByProductId(Long productId);
}