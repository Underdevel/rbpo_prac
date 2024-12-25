package ru.mtuci.rbpo_2024_praktika.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.rbpo_2024_praktika.model.License;
import ru.mtuci.rbpo_2024_praktika.model.User;
import ru.mtuci.rbpo_2024_praktika.model.UserDetailsImpl;
import ru.mtuci.rbpo_2024_praktika.service.LicenseService;
import ru.mtuci.rbpo_2024_praktika.service.UserService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/license")
public class LicenseController {

    private final LicenseService licenseService;
    private final UserService userService;


    @PostMapping("/renew")
    public ResponseEntity<?> renewLicense(@RequestParam String licenseCode, Authentication authentication){
        User user = getAuthenticatedUser();
        try {
            return ResponseEntity.ok(licenseService.renewLicense(licenseCode, userService.findUserByLogin(user.getLogin()).orElse(null)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activateLicense(@RequestParam(name = "activationCode") String activationCode,
                                             @RequestBody Map<String,Object> params, Authentication authentication) {
        User user = getAuthenticatedUser();
        try {
            return ResponseEntity.status(HttpStatus.OK).body(licenseService.activateLicense(activationCode, params, userService.findUserByLogin(user.getLogin()).orElse(null)));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createLicenseWithParams(@RequestParam(name = "productId") Long productId,
                                                     @RequestParam(name = "ownerId") Long ownerId,
                                                     @RequestParam(name = "licenseTypeId") Long licenseTypeId,
                                                     @RequestBody Map<String, Object> params) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(licenseService.createLicense(productId, ownerId, licenseTypeId, params));
        } catch (IllegalArgumentException ex) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }


    @GetMapping("/read")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllLicenses() {
        return ResponseEntity.ok(licenseService.getAllLicenses());
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getLicenseById(@PathVariable Long id) {
        return ResponseEntity.ok(licenseService.findLicenseById(id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateLicense(@PathVariable Long id, @RequestBody License license) {
        return ResponseEntity.ok(licenseService.updateLicense(license));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteLicenseById(@PathVariable Long id) {
        licenseService.deleteLicenseById(id);
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