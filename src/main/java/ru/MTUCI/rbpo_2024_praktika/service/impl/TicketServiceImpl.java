package ru.MTUCI.rbpo_2024_praktika.service.impl;

import org.springframework.stereotype.Service;
import ru.MTUCI.rbpo_2024_praktika.model.Device;
import ru.MTUCI.rbpo_2024_praktika.model.License;
import ru.MTUCI.rbpo_2024_praktika.model.Ticket;
import ru.MTUCI.rbpo_2024_praktika.service.TicketService;

import java.util.Date;

@Service
public class TicketServiceImpl implements TicketService {

    @Override
    public Ticket generateTicket(License license, Device device) {
        Ticket ticket = new Ticket();

        ticket.setServerDate(new Date());
        ticket.setTicketLifetime(7200L);
        ticket.setActivationDate(license.getFirst_activation_date());
        ticket.setExpirationDate(license.getEnding_date());
        ticket.setUserId(license.getOwner().getId());
        ticket.setDeviceId(device.getId());
        ticket.setBlocked(license.getBlocked());

        ticket.generateDigitalSignature();
        return ticket;
    }

    @Override
    public Ticket generateTicket(License license) {
        Ticket ticket = new Ticket();

        ticket.setServerDate(new Date());
        ticket.setTicketLifetime(7200L);
        ticket.setActivationDate(license.getFirst_activation_date());
        ticket.setExpirationDate(license.getEnding_date());
        ticket.setUserId(license.getOwner().getId());
        ticket.setBlocked(license.getBlocked());

        ticket.generateDigitalSignature();
        return ticket;
    }

    @Override
    public Ticket generateRenewalTicket(License license) {
        Ticket ticket = new Ticket();

        ticket.setServerDate(new Date());
        ticket.setTicketLifetime(7200L);
        ticket.setActivationDate(license.getFirst_activation_date());
        ticket.setExpirationDate(license.getEnding_date());
        ticket.setUserId(license.getOwner().getId());
        ticket.setBlocked(license.getBlocked());

        ticket.generateDigitalSignature(true);
        return ticket;
    }
}