package ru.MTUCI.rbpo_2024_praktika.service;

import ru.MTUCI.rbpo_2024_praktika.model.Device;
import ru.MTUCI.rbpo_2024_praktika.model.User;

import java.util.List;
import java.util.Optional;

public interface DeviceService {
    List<Device> getAllDevices();
    Optional<Device> findDeviceById(Long id);
    Optional<Device> findDeviceByMacAddress(String macAddress);

    Device createDevice(Device device, User user);
    Device updateDevice(Device device);

    void deleteDeviceById(Long id);
}