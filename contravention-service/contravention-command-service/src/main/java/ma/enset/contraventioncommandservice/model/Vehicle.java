package ma.enset.contraventioncommandservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.coreapi.VehicleType;

import javax.annotation.Nonnull;
import javax.persistence.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Vehicle {
    @Id
    private String registrationNumber;
    @Enumerated(EnumType.STRING)
    private VehicleType type;
    private String model;
    private int fiscalPower;
    @ManyToOne
    private VehicleOwner vehicleOwner;
}
