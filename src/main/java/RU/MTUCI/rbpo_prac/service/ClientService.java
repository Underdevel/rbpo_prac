package RU.MTUCI.rbpo_prac.service;

import RU.MTUCI.rbpo_prac.model.Client;

import java.util.List;

public interface ClientService {
    void save(Client client);
    List<Client> findAll();
    Client findById(long id);
}