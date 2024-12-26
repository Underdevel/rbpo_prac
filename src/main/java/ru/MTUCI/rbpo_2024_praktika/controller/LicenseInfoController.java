package ru.MTUCI.rbpo_2024_praktika.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.MTUCI.rbpo_2024_praktika.model.User;
import ru.MTUCI.rbpo_2024_praktika.service.LicenseService;
import ru.MTUCI.rbpo_2024_praktika.service.UserService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/license/info")
public class LicenseInfoController {

    private final LicenseService licenseService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getLicenseInfo(@RequestBody Map<String, Object> deviceInfo, Authentication authentication) {
        User user = getAuthenticatedUser();

        try {
            return ResponseEntity.ok(licenseService.findLicenseInfo(deviceInfo, userService.findUserByLogin(user.getLogin()).orElse(null)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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