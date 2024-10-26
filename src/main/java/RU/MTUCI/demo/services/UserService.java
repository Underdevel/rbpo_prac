package RU.MTUCI.demo.services;

import RU.MTUCI.demo.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getAll();
    void add(User user);
    User getById(UUID id);
    User getByName(String name);
}
