package RU.MTUCI.demo.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import RU.MTUCI.demo.model.User;
import RU.MTUCI.demo.repo.UserRepository;
import RU.MTUCI.demo.services.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void add(User user) {
        if (userRepository.findByName(user.getName()).isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    @Override
    public User getById(UUID id) {
        return userRepository.findById(id).orElse(new User());
    }

    @Override
    public User getByName(String name)  {
        return userRepository.findByName(name).orElse(new User());
    }

}
