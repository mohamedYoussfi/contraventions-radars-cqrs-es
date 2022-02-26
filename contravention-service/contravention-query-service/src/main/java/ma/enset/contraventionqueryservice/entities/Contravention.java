package ma.enset.contraventionqueryservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.coreapi.ContraventionStatus;
import ma.enset.coreapi.VehicleType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.Instant;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Contravention {
   @Id
   private String contraventionId;
   private Instant instant;
   private String radarId;
   private double radarLong;
   private double radarLat;
   private double radarAlt;
   private String vehicleRegistrationNumber;
   private int vehicleSpeed;
   private int radarMaxSpeed;
   private String ownerNationalCardId;
   @Enumerated(EnumType.STRING)
   private VehicleType vehicleType;
   private double amount;
   private String vehicleOwner;
   private String ownerEmail;
   private String ownerPhoneNumber;
   private  String ownerAddress;
   @Enumerated(EnumType.STRING)
   private ContraventionStatus status;

}
