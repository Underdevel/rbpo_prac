package ru.MTUCI.rbpo_2024_praktika.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        return deviceService.findDeviceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Device> createDevice(@RequestBody Device device) {
        User user = getAuthenticatedUser();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(deviceService.createDevice(device, user));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateDevice(@PathVariable Long id, @RequestBody Device device) {
        if (!deviceService.findDeviceById(id).isPresent()){
            return ResponseEntity.notFound().build();
        }
        device.setId(id);
        return ResponseEntity.ok(deviceService.updateDevice(device));
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
}