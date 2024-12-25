package ru.MTUCI.rbpo_2024_praktika.controller.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String email;
    private String token;
}