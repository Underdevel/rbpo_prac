package ru.MTUCI.rbpo_2024_praktika.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LicenseActivationRequest {
    private String mac;
    private String name;
}