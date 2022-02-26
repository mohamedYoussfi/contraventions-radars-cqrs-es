package com.example.imatriculatonqueryservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class VehicleOwner {
    @Id
    private String id;
    private String ownerName;
    private String ownerNationalIdCard;
    private String ownerEmail;
    private String ownerPhoneNumber;
    private String ownerAddress;
    @OneToMany(mappedBy = "vehicleOwner")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<VehicleOwnerShip> vehicleOwnerShips;
}
