package ru.MTUCI.rbpo_2024_praktika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.MTUCI.rbpo_2024_praktika.model.License;
import ru.MTUCI.rbpo_2024_praktika.model.LicenseHistory;
import ru.MTUCI.rbpo_2024_praktika.model.User;
import ru.MTUCI.rbpo_2024_praktika.repository.LicenseHistoryRepository;
import ru.MTUCI.rbpo_2024_praktika.service.LicenseHistoryService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//TODO: 1. updateLicenseHistory - не нужно изменять историю никогда
//TODO: 2. deleteLicenseHistoryById - удалять тоже не нужно
//TODO: 3. А вот возможность получить историю на клиенте или админу точно нужно

@Service
@RequiredArgsConstructor
public class LicenseHistoryServiceImpl implements LicenseHistoryService {

    private final LicenseHistoryRepository licenseHistoryRepository;

    @Override
    public Optional<LicenseHistory> findLicenseHistoryById(Long id) {
        return licenseHistoryRepository.findById(id);
    }

    @Override
    public List<LicenseHistory> findAllLicenseHistories() {
        return licenseHistoryRepository.findAll();
    }

    @Override
    public void createLicenseHistory(License license, User user, String action, String description) {
            LicenseHistory licenseHistory = new LicenseHistory();
            licenseHistory.setLicense(license);
            licenseHistory.setDescription(description);
            licenseHistory.setStatus(action);
            licenseHistory.setUser(user);
            licenseHistory.setChangeDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            licenseHistoryRepository.save(licenseHistory);

    }

    /*@Override
    public LicenseHistory updateLicenseHistory(LicenseHistory licenseHistory) {
        if (!licenseHistoryRepository.existsById(licenseHistory.getId())){
            throw new IllegalStateException("License history not found: " + licenseHistory.getId());
        }
        return licenseHistoryRepository.save(licenseHistory);
    }

    @Override
    public void deleteLicenseHistoryById(Long id) {
        if (!licenseHistoryRepository.existsById(id)){
            throw new IllegalStateException("License history not found: " + id);
        }
        licenseHistoryRepository.deleteById(id);
    }*/

    @Override
    public List<LicenseHistory> findAllLicenseHistoriesByLicenseId(Long licenseId) {
        return licenseHistoryRepository.findAllByLicenseId(licenseId);
    }

    @Override
    public List<LicenseHistory> findAllLicenseHistoriesByUserId(Long userId) {
        return licenseHistoryRepository.findAllByUserId(userId);
    }
}