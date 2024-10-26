package RU.MTUCI.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import RU.MTUCI.demo.model.License;

import java.util.Optional;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {
    Optional<License> findByKey(String key);
}
