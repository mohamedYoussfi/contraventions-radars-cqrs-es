package com.example.imatriculatonqueryservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class VehicleOwnerShip {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant instant;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Vehicle vehicle;
    @ManyToOne
    private VehicleOwner vehicleOwner;
}
