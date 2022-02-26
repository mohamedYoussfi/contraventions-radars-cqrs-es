package ma.enset.radarqueryservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.Instant;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class OverSpeedDetection {
    @Id
    private String overSpeedId;
    private Instant timeStamp;
    private String vehicleRegistrationNumber;
    private int vehicleSpeed;
    @ManyToOne
    private Radar radar;


}
