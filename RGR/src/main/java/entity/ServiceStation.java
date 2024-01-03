package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"Service Station\"")
public class ServiceStation {
    @Id
    private String name;
    @Column(name = "address_city")
    private String addressCity;
    @Column(name = "address_building")
    private String addressBuilding;
    @Column(name = "max_cars")
    private int maxCars;
}
