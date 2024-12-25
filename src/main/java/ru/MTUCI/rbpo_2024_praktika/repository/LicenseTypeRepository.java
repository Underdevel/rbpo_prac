package ru.MTUCI.rbpo_2024_praktika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.MTUCI.rbpo_2024_praktika.model.LicenseType;

import java.util.List;
import java.util.Optional;

@Repository
public interface LicenseTypeRepository extends JpaRepository<LicenseType, Long> {
    Optional<LicenseType> findById(Long id);
    List<LicenseType> findAll();
    LicenseType save(LicenseType licenseType);
    void deleteById(Long id);
    boolean existsById(Long id);
}
