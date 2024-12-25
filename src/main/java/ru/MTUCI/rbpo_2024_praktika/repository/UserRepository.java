package ru.mtuci.rbpo_2024_praktika.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mtuci.rbpo_2024_praktika.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByLogin(String login);
    Optional<User> findById(Long id);
    Optional<User> findByLoginAndEmail(String login, String email);

    Boolean existsByLogin(String login);
    Boolean existsByEmail(String email);
}