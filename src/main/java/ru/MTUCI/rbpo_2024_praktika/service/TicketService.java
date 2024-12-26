package ru.MTUCI.rbpo_2024_praktika.service;

import ru.MTUCI.rbpo_2024_praktika.model.Device;
import ru.MTUCI.rbpo_2024_praktika.model.License;
import ru.MTUCI.rbpo_2024_praktika.model.Ticket;

public interface TicketService {
    Ticket generateTicket(License license, Device device);
    Ticket generateTicket(License license);
    Ticket generateRenewalTicket(License license);
}
