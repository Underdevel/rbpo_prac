package RU.MTUCI.rbpo_prac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import RU.MTUCI.rbpo_prac.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByLogin(String login);
}