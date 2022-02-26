package com.example.imatriculatonqueryservice.repositories;

import com.example.imatriculatonqueryservice.entities.Vehicle;
import com.example.imatriculatonqueryservice.entities.VehicleOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleOwnerRepository extends JpaRepository<VehicleOwner, String> {
    VehicleOwner findByOwnerNationalIdCard(String nationalIdCard );
}
