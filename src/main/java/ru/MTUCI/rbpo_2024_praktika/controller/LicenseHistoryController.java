package ru.MTUCI.rbpo_2024_praktika.controller;

import org.springframework.web.bind.annotation.*;
import ru.MTUCI.rbpo_2024_praktika.model.LicenseHistory;
import ru.MTUCI.rbpo_2024_praktika.service.LicenseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/license/history")
public class LicenseHistoryController {

    private final LicenseHistoryService licenseHistoryService;

    @GetMapping("/read")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LicenseHistory>> findAllLicenseHistories() {
        return ResponseEntity.ok(licenseHistoryService.findAllLicenseHistories());
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LicenseHistory> findLicenseHistoryById(@PathVariable Long id) {
        return licenseHistoryService.findLicenseHistoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}