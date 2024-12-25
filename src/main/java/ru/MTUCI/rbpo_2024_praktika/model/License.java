package ru.MTUCI.rbpo_2024_praktika.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="licenses")
public class License {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private LicenseType licenseType;

    @Column(name = "first_activation_date")
    private Date first_activation_date;

    @Column(name = "ending_date")
    private Date ending_date;

    @Column(name = "blocked")
    private Boolean blocked;

    @Column(name = "devices_count")
    private Integer devicesCount;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @Column(name = "duration")
    private int duration;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "license", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("license")
    private List<LicenseHistory> licenseHistories;

    @OneToMany(mappedBy = "license", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("license")
    private List<DeviceLicense> deviceLicenses;
}