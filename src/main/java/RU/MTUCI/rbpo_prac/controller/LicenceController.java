package RU.MTUCI.rbpo_prac.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import RU.MTUCI.rbpo_prac.model.Client;
import RU.MTUCI.rbpo_prac.model.Licence;
import RU.MTUCI.rbpo_prac.repository.LicenceRepository;
import RU.MTUCI.rbpo_prac.service.ClientService;

@RestController
@RequestMapping("/licence")
@RequiredArgsConstructor
public class LicenceController {

    private final LicenceRepository licenceRepository;
    private final ClientService clientService;

    @PostMapping("/{client_id}/save")
    public void save(@PathVariable(value = "client_id") Long clientId,
                     @RequestBody Licence licence) {
        Client client = clientService.findById(clientId);
        licence.setClient(client);
        licenceRepository.save(licence);
    }
}