package ru.MTUCI.rbpo_2024_praktika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.MTUCI.rbpo_2024_praktika.model.Device;
import ru.MTUCI.rbpo_2024_praktika.model.User;
import ru.MTUCI.rbpo_2024_praktika.repository.DeviceRepository;
import ru.MTUCI.rbpo_2024_praktika.service.DeviceService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    @Override
    public Optional<Device> findDeviceById(Long id) {
        return deviceRepository.findById(id);
    }

    @Override
    public List<Device> findAllDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public Device createDevice(Device device, User user) {

        if (device.getId() != null && deviceRepository.existsById(device.getId())) {
            throw new IllegalStateException("Device with this id already exists: " + device.getId());
        }
        if (deviceRepository.existsByMacAddress(device.getMacAddress())){
            throw new IllegalStateException("Device with this mac address already exists: " + device.getMacAddress());
        }

        device.setUser(user);
        return deviceRepository.save(device);
    }

    public Device updateDevice(Device device, User user) {
        Device existingDevice = deviceRepository.findById(device.getId()).orElseThrow(() -> new IllegalStateException("Device not found: " + device.getId()));

        if (!existingDevice.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("You are not the owner of this device: " + device.getId());
        }

        existingDevice.setMacAddress(device.getMacAddress());
        existingDevice.setName(device.getName());


        return deviceRepository.save(existingDevice);
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