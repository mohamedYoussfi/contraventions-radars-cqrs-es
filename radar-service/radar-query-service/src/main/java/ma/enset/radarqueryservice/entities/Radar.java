package ma.enset.radarqueryservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.coreapi.RadarStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Radar {
    @Id
    private String radarId;
    private String name;
    private double longitude;
    private double latitude;
    private  double altitude;
    private int maxSpeed;
    private String roadDesignation;
    private RadarStatus radarStatus;
    @OneToMany(mappedBy = "radar")
    private List<OverSpeedDetection> overSpeedDetections;
}
