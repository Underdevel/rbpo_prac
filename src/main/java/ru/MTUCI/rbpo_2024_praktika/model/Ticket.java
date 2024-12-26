package ru.MTUCI.rbpo_2024_praktika.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public void generateDigitalSignature(boolean renewal) {
        try {
            String dataToSign = serverDate.toString() +
                    ticketLifetime.toString() +
                    (activationDate != null ? activationDate.toString() : "") +
                    (expirationDate != null ? expirationDate.toString() : "")+
                    userId.toString() +
                    (renewal ? "" : (deviceId != null ? deviceId.toString() : "" )) +
                    blocked.toString();

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(dataToSign.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, digest);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }
            this.digitalSignature = hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            this.digitalSignature = null;
            this.errorMessage = "Failed to generate digital signature: " + e.getMessage();
        }
    }
    public void generateDigitalSignature() {
        generateDigitalSignature(false);
    }
}