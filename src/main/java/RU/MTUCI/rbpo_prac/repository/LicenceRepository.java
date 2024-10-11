package RU.MTUCI.rbpo_prac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import RU.MTUCI.rbpo_prac.model.Licence;

@Repository
public interface LicenceRepository extends JpaRepository<Licence, Long> {
}