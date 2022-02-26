package ma.enset.contraventioncommandservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class VehicleOwner {
    @Id
    private String id;
    private String nationalCardNumber;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
}
