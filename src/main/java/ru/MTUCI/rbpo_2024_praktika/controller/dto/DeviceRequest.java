package ru.MTUCI.rbpo_2024_praktika.controller.dto;

import lombok.Data;
@Data
public class DeviceRequest {
    private String macAddress;
    private String name;
}