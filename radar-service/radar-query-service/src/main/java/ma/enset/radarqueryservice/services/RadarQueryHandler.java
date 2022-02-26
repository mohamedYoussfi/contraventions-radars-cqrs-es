package ma.enset.radarqueryservice.services;

import ma.enset.coreapi.*;
import ma.enset.radarqueryservice.entities.OverSpeedDetection;
import ma.enset.radarqueryservice.entities.Radar;
import ma.enset.radarqueryservice.mappers.RadarMappers;
import ma.enset.radarqueryservice.repositories.OverSpeedDetectionRepository;
import ma.enset.radarqueryservice.repositories.RadarRepository;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RadarQueryHandler {
    private RadarRepository radarRepository;
    private OverSpeedDetectionRepository overSpeedDetectionRepository;
    private RadarMappers radarMappers;


    public RadarQueryHandler(RadarRepository radarRepository, OverSpeedDetectionRepository overSpeedDetectionRepository, RadarMappers radarMappers) {
        this.radarRepository = radarRepository;
        this.overSpeedDetectionRepository = overSpeedDetectionRepository;
        this.radarMappers = radarMappers;
    }
    @QueryHandler
    public List<RadarResponseDTO>  handler(GetAllRadarsQuery query){
        List<Radar> allRadars = radarRepository.findAll();
        return allRadars.stream().map(radar->radarMappers.from(radar))
                .collect(Collectors.toList());
    }

    @QueryHandler
    public RadarOverSpeedsDTO  handler(GetRadarById query){
        Radar radar=radarRepository.findById(query.getRadarId())
                .orElseThrow(()->new RuntimeException("Radar Not found"));
        RadarOverSpeedsDTO radarOverSpeedsDTO=radarMappers.fromRadar(radar);
        List<OverSpeedResponseDTO> list = radar.getOverSpeedDetections()
                .stream()
                .map(os -> radarMappers.fromOS(os))
                .collect(Collectors.toList());
        radarOverSpeedsDTO.setOverSpeedDetections(list);
        return radarOverSpeedsDTO;
    }
    @QueryHandler
    public List<OverSpeedResponseDTO>  handler(GetOverSpeedsQuery query){
        List<OverSpeedDetection> overSpeedDetections = overSpeedDetectionRepository.findAll();
        List<OverSpeedResponseDTO> overSpeedResponseDTOS = overSpeedDetections.stream()
                .map(os -> radarMappers.fromOS(os)).collect(Collectors.toList());
        return overSpeedResponseDTOS;
    }
    @QueryHandler
    public List<EventDataResponseDTO> handle(SubscribeToEventsQuery query) {
        return new ArrayList<>();
    }

    @QueryHandler
    public List<OverSpeedResponseDTO> overSpeedDetections(GetAllOverSpeedsQuery query){
        List<OverSpeedDetection> all = overSpeedDetectionRepository.findAll();
        return all.stream()
                .map(os->radarMappers.fromOS(os))
                .collect(Collectors.toList());
    }

    @QueryHandler
    public List<OverSpeedResponseDTO> overSpeedsByRegNumber(GetAllOverSpeedsByRegistrationNumberQuery query){
        List<OverSpeedDetection> all = overSpeedDetectionRepository.findByVehicleRegistrationNumber(query.getRegistrationNumber());
        return all.stream()
                .map(os->radarMappers.fromOS(os))
                .collect(Collectors.toList());
    }
}
