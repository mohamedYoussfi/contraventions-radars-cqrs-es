package com.example.imatriculatonqueryservice.repositories;

import com.example.imatriculatonqueryservice.entities.VehicleOwner;
import com.example.imatriculatonqueryservice.entities.VehicleOwnerShip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleOwnerShipRepository extends JpaRepository<VehicleOwnerShip,Long> {
}
