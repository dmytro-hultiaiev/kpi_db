package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"Issuing\"")
public class Issuing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String vin;
    @Column(name = "№_drivers_licensce")
    private String driversLicense;
    @Column(name = "date_issue")
    private LocalDate dateIssue;
    @Column(name = "date_return", nullable = true)
    private LocalDate dateReturn;
}
