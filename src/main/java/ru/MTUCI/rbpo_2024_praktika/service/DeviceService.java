package ru.MTUCI.rbpo_2024_praktika.service;

import ru.MTUCI.rbpo_2024_praktika.controller.dto.LicenseActivationRequest;
import ru.MTUCI.rbpo_2024_praktika.model.Device;
import ru.MTUCI.rbpo_2024_praktika.model.User;

import java.util.List;
import java.util.Optional;

public interface DeviceService {
    List<Device> findAllDevices();
    Optional<Device> findDeviceById(Long id);
    Device  findDeviceByMacAddress(String macAddress);

    Device registerOrUpdateDevice(LicenseActivationRequest licenseActivationRequest, User user);

    Device createDevice(Device device, User user);
    Device updateDevice(Device device, User user);

    Device findDeviceByInfo(String mac, String name, User user);

    void deleteDeviceById(Long id);
}