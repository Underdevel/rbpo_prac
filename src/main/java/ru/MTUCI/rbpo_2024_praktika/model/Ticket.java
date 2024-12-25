package ru.MTUCI.rbpo_2024_praktika.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    private Date serverDate;
    private Long ticketLifetime;
    private Date activationDate;
    private Date expirationDate;
    private Long userId;
    private Long deviceId;
    private Boolean blocked;
    private String digitalSignature;
    private String errorMessage;
}