package RU.MTUCI.rbpo_prac.controller;

import org.springframework.web.bind.annotation.*;
import RU.MTUCI.rbpo_prac.model.Client;
import RU.MTUCI.rbpo_prac.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> findAll() {
        return clientService.findAll();
    }

    @PostMapping("/save")
    public void save(@RequestBody Client client) {
        clientService.save(client);
    }
}