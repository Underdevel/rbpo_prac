package ru.mtuci.rbpo_2024_praktika.controller.dto;

import lombok.Data;

@Data
public class RegRequest {
    private String login;
    private String email;
    private String password;
}