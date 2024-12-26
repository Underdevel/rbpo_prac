package ru.MTUCI.rbpo_2024_praktika.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LicenseInfoRequest {
    private String mac;
    private String name;
}