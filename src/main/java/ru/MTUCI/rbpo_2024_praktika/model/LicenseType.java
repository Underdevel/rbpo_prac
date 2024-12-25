package ru.mtuci.rbpo_2024_praktika.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "license_types")
@Entity
public class LicenseType {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "default_duration")
    private Integer defaultDuration;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "licenseType", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("licenseType")
    private List<License> license;

}