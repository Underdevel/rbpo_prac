package ru.MTUCI.rbpo_2024_praktika.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.MTUCI.rbpo_2024_praktika.controller.dto.DeviceRequest;
import ru.MTUCI.rbpo_2024_praktika.model.Device;
import ru.MTUCI.rbpo_2024_praktika.model.User;
import ru.MTUCI.rbpo_2024_praktika.service.DeviceService;
import ru.MTUCI.rbpo_2024_praktika.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;
    private final UserService userService;

    @GetMapping("/read")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Device>> getAllDevices() {
        return ResponseEntity.ok(deviceService.findAllDevices());
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        return deviceService.findDeviceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Device> createDevice(@RequestBody DeviceRequest DeviceRequest) {
        User user = getAuthenticatedUser();
        Device device = convertToEntity(DeviceRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(deviceService.createDevice(device, user));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable Long id, @RequestBody DeviceRequest DeviceRequest) {
        User user = getAuthenticatedUser();
        Device device = convertToEntity(DeviceRequest);
        device.setId(id);
        return ResponseEntity.ok(deviceService.updateDevice(device, user));
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteDeviceById(@PathVariable Long id) {
        if (!deviceService.findDeviceById(id).isPresent()){
            return ResponseEntity.notFound().build();
        }
        deviceService.deleteDeviceById(id);
        return ResponseEntity.ok("Successful");
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = (String) authentication.getPrincipal();
            return userService.findUserByEmail(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        }
        return null;
    }
    private Device convertToEntity(DeviceRequest DeviceRequest) {
        Device device = new Device();
        device.setMacAddress(DeviceRequest.getMacAddress());
        device.setName(DeviceRequest.getName());
        return device;
    }
}