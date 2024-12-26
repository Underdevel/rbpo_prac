package ru.MTUCI.rbpo_2024_praktika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.MTUCI.rbpo_2024_praktika.controller.dto.LicenseActivationRequest;
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
    public Device findDeviceByMacAddress(String macAddress) {
        return deviceRepository.findDeviceByMacAddress(macAddress);
    }

    public Device registerOrUpdateDevice(LicenseActivationRequest licenseActivationRequest, User user) {
        String mac = licenseActivationRequest.getMac();
        if (mac.isEmpty()) {
            throw new IllegalArgumentException("Mac can`t be empty");
        }

        String name = licenseActivationRequest.getName();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Device Name can`t be empty");
        }
        Device device = new Device();
        if(deviceRepository.existsByMacAddress((mac))){
         device = deviceRepository.findDeviceByMacAddress(mac);
        } else {
             device = new Device();
            device.setMacAddress(mac);
            device.setName(name);
            device.setUser(user);
        }
        return deviceRepository.save(device);
    }

    public Device findDeviceByInfo(String mac, String name, User user) {
        Device device = deviceRepository.findDeviceByMacAddress(mac);
        return device;
    }
}