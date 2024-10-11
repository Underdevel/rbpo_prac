package RU.MTUCI.rbpo_prac.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "licence")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Licence {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "data_start")
    private String start;

    @Column(name = "data_end")
    private String end;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @JsonIgnoreProperties("licence")
    private Client client;
}