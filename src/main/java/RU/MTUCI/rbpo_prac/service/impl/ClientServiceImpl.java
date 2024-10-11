package RU.MTUCI.rbpo_prac.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import RU.MTUCI.rbpo_prac.model.Client;
import RU.MTUCI.rbpo_prac.repository.ClientRepository;
import RU.MTUCI.rbpo_prac.repository.LicenceRepository;
import RU.MTUCI.rbpo_prac.service.ClientService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final LicenceRepository licenceRepository;

    @Override
    public void save(Client client) {
        client.getLicence().forEach(details -> {
            details.setClient(client);
            licenceRepository.save(details);
        });
        clientRepository.save(client);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(long id) {
        return clientRepository.findById(id).orElse(new Client());
    }
}