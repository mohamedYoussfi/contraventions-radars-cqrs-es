package ma.enset.radardevice.model;

import lombok.Data;

@Data
public class OverSpeedDetection {
    private String radarId;
    private String vehicleRegistrationNumber;
    private int vehicleSpeed;
}
