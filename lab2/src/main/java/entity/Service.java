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
@Table(name = "\"Service\"")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String vin;
    @Column(name = "service_name")
    private String serviceName;
    @Column(name = "service_start_date")
    private LocalDate serviceStartDate;
    @Column(name = "service_end_date")
    private LocalDate serviceEndDate;
}
