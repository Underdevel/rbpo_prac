package ru.MTUCI.rbpo_2024_praktika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.MTUCI.rbpo_2024_praktika.model.Device;
import ru.MTUCI.rbpo_2024_praktika.model.User;
import ru.MTUCI.rbpo_2024_praktika.repository.DeviceRepository;
import ru.MTUCI.rbpo_2024_praktika.service.DeviceService;

import java.util.List;
import java.util.Optional;

//TODO: 1. createDevice - вы каждый раз сохраняете новое устройство, даже если такое уже есть?
//TODO: 2. updateDevice - откуда вы получаете сущность для метода?

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    @Override
    public Optional<Device> findDeviceById(Long id) {
        return deviceRepository.findById(id);
    }

    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public Device createDevice(Device device, User user) {
        device.setUser(user);
        return deviceRepository.save(device);
    }

    @Override
    public Device updateDevice(Device device) {
        if (!deviceRepository.existsById(device.getId())){
            throw new IllegalStateException("Device not found: " + device.getId());
        }
        return deviceRepository.save(device);
    }

    @Override
    public void deleteDeviceById(Long id) {
        if (!deviceRepository.existsById(id)){
            throw new IllegalStateException("Device not found: " + id);
        }
        deviceRepository.deleteById(id);
    }

    @Override
    public Optional<Device> findDeviceByMacAddress(String macAddress) {
        return deviceRepository.findDeviceByMacAddress(macAddress);
    }
}