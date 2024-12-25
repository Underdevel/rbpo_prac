package ru.mtuci.rbpo_2024_praktika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpo_2024_praktika.model.LicenseType;
import ru.mtuci.rbpo_2024_praktika.repository.LicenseTypeRepository;
import ru.mtuci.rbpo_2024_praktika.service.LicenseTypeService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LicenseTypeServiceImpl implements LicenseTypeService {

    private final LicenseTypeRepository licenseTypeRepository;

    @Override
    public Optional<LicenseType> findLicenseTypeById(Long id) {
        return licenseTypeRepository.findById(id);
    }

    @Override
    public List<LicenseType> getAllLicenseTypes() {
        return licenseTypeRepository.findAll();
    }

    @Override
    public LicenseType createLicenseType(LicenseType licenseType) {
        return licenseTypeRepository.save(licenseType);
    }

    @Override
    public LicenseType updateLicenseType(LicenseType licenseType) {
        if (!licenseTypeRepository.existsById(licenseType.getId())){
            throw new IllegalStateException("License type not found: " + licenseType.getId());
        }
        return licenseTypeRepository.save(licenseType);
    }

    @Override
    public void deleteLicenseTypeById(Long id) {
        if (!licenseTypeRepository.existsById(id)){
            throw new IllegalStateException("License type not found: " + id);
        }
        licenseTypeRepository.deleteById(id);
    }
}