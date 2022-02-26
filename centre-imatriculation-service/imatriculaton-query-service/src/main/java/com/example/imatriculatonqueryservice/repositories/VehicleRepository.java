package com.example.imatriculatonqueryservice.repositories;

import com.example.imatriculatonqueryservice.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
}
