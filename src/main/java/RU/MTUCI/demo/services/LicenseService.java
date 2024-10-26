package RU.MTUCI.demo.services;

import RU.MTUCI.demo.model.License;

import java.util.List;

public interface LicenseService {
    void add(License license);

    List<License> getAll();

    License getById(Long id);

    License getByKey(String key);
}
