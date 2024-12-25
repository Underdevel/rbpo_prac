package ru.MTUCI.rbpo_2024_praktika.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.MTUCI.rbpo_2024_praktika.model.LicenseType;
import ru.MTUCI.rbpo_2024_praktika.service.LicenseTypeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/license/type")
public class LicenseTypeController {

    private final LicenseTypeService licenseTypeService;

    @GetMapping("/read")
    public ResponseEntity<List<LicenseType>> getAllLicenseTypes() {
        return ResponseEntity.ok(licenseTypeService.getAllLicenseTypes());
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<LicenseType> getLicenseTypeById(@PathVariable Long id) {
        return licenseTypeService.findLicenseTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createLicenseType(@RequestBody LicenseType licenseType) {
        if(licenseType.getId() != null && licenseTypeService.findLicenseTypeById(licenseType.getId()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("License type with id: " + licenseType.getId() + " already exist");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(licenseTypeService.createLicenseType(licenseType));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LicenseType> updateLicenseType(@PathVariable Long id, @RequestBody LicenseType licenseType) {
        if (!licenseTypeService.findLicenseTypeById(id).isPresent()){
            return ResponseEntity.notFound().build();
        }
        licenseType.setId(id);
        return ResponseEntity.ok(licenseTypeService.updateLicenseType(licenseType));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteLicenseTypeById(@PathVariable Long id) {
        if(!licenseTypeService.findLicenseTypeById(id).isPresent()){
            return ResponseEntity.notFound().build();
        }
        licenseTypeService.deleteLicenseTypeById(id);
        return ResponseEntity.ok("Successful");
    }
}