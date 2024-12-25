package ru.mtuci.rbpo_2024_praktika.service;

import ru.mtuci.rbpo_2024_praktika.model.ApplicationRole;
import ru.mtuci.rbpo_2024_praktika.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User registerUser(String login, String password, String email, ApplicationRole role);

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByLogin(String login);
    Optional<User> findUserById(Long id);
    Optional<User> findUserByLoginAndEmail(String login, String email);

    Boolean existsUserByLogin(String login);
    Boolean existsUserByEmail(String email);
}
