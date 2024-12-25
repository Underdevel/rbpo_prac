package ru.mtuci.rbpo_2024_praktika.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpo_2024_praktika.model.ApplicationRole;
import ru.mtuci.rbpo_2024_praktika.model.User;
import ru.mtuci.rbpo_2024_praktika.repository.DeviceRepository;
import ru.mtuci.rbpo_2024_praktika.repository.UserRepository;
import ru.mtuci.rbpo_2024_praktika.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private DeviceRepository deviceRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(String login, String password, String email, ApplicationRole role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("Email already taken: " + email);
        }

        if (userRepository.findByLogin(login).isPresent()) {
            throw new IllegalStateException("Login already taken: " + login);
        }


        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setEmail(email);
        newUser.setRole(role);
        return userRepository.save(newUser);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new IllegalArgumentException("Login cannot be null or empty");
        }
        return userRepository.findByLogin(login);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        if (id == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        return userRepository.findById(id);

    }

    @Override
    public Optional<User> findUserByLoginAndEmail(String login, String email) {
        if (login == null || login.isEmpty() ) {
            throw new IllegalArgumentException("Login cannot be null or empty");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        return userRepository.findByLoginAndEmail(login, email);
    }

    @Override
    public Boolean existsUserByLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new IllegalArgumentException("Login cannot be null or empty");
        }
        return userRepository.existsByLogin(login);
    }

    @Override
    public Boolean existsUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        return userRepository.existsByEmail(email);
    }
}
