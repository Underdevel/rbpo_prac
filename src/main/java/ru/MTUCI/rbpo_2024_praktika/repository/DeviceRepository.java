package ru.MTUCI.rbpo_2024_praktika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.MTUCI.rbpo_2024_praktika.model.Device;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findAllByUserId(Long userId);
    Optional<Device> findById(Long id);
    Device findDeviceByMacAddress(String macAddress);
    Boolean existsByMacAddress(String mac);
}