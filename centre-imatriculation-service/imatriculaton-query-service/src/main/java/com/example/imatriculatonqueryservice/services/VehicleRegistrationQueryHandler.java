package com.example.imatriculatonqueryservice.services;

import com.example.imatriculatonqueryservice.entities.Vehicle;
import com.example.imatriculatonqueryservice.entities.VehicleOwner;
import com.example.imatriculatonqueryservice.repositories.VehicleOwnerRepository;
import com.example.imatriculatonqueryservice.repositories.VehicleOwnerShipRepository;
import com.example.imatriculatonqueryservice.repositories.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import ma.enset.coreapi.GetAllOwners;
import ma.enset.coreapi.GetAllVehiclesQuery;
import ma.enset.coreapi.GetVehicleByRegistrationNumber;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class VehicleRegistrationQueryHandler {
    private VehicleRepository vehicleRepository;
    private VehicleOwnerRepository vehicleOwnerRepository;
    private VehicleOwnerShipRepository vehicleOwnerShipRepository;

    public VehicleRegistrationQueryHandler(VehicleRepository vehicleRepository, VehicleOwnerRepository vehicleOwnerRepository, VehicleOwnerShipRepository vehicleOwnerShipRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleOwnerRepository = vehicleOwnerRepository;
        this.vehicleOwnerShipRepository = vehicleOwnerShipRepository;
    }
    @QueryHandler
    public List<Vehicle> vehicles(GetAllVehiclesQuery query){
        return vehicleRepository.findAll();
    }

    @QueryHandler
    public List<VehicleOwner> owners(GetAllOwners query){
        return vehicleOwnerRepository.findAll();
    }
    @QueryHandler
    public Vehicle vehicleByReNumber(GetVehicleByRegistrationNumber query){
        return vehicleRepository.findById(query.getRegistrationNumber())
                .orElseThrow(()->new RuntimeException("Vehicle Registration Number does not exist"));
    }

}
