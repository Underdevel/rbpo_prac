package ru.MTUCI.rbpo_2024_praktika.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

//TODO: 1. В проекте отсутствует алгоритм генерации цифровой подписи для тикета
//TODO: 2. Класс не используется в проекте вообще

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