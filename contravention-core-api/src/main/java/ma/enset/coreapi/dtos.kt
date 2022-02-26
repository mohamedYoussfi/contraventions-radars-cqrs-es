package ma.enset.coreapi

import java.time.Instant

data class RadarDTO(
    var radarId : String ="",
    var name : String="",
    var longitude : Double=0.0,
    var latitude :Double=0.0,
    var altitude : Double=0.0,
    var maxSpeed : Int=0,
    var roadDesignation : String="",
    var radarStatus : RadarStatus=RadarStatus.OFF
    );
data class ChangeRadarStatusRequestDTO(
    val radarId : String,
    val radarStatus : RadarStatus
);
data class ChangeRadarSpeedLimitRequestDTO(
    val radarId : String,
    val speed : Int
);
open class OverSpeedRequestDTO(
    var radarId : String="",
    var timeStamp : Instant= Instant.now(),
    var overSpeedId : String="",
    var vehicleRegistrationNumber : String="",
    var vehicleType : VehicleType =VehicleType.CAR,
    var vehicleSpeed : Int=0,
    var radarMaxSpeed: Int=0,
    var radarLongitude : Double=0.0,
    var radarLatitude : Double=0.0,
    var radarAltitude : Double=0.0,
);

data class ContraventionData(
    var contraventionId : String="",
    var amount : Double=0.0,
    var vehicleOwner : String ="",
    var ownerEmail : String ="",
    var ownerPhoneNumber : String ="",
    var ownerAddress : String ="",
    var ownerNationalCardId: String="",
    var status : ContraventionStatus=ContraventionStatus.PENDING,
) : OverSpeedRequestDTO();

data class OverSpeedResponseDTO(
    var overSpeedId : String="",
    var timeStamp : Instant= Instant.now(),
    var radarId : String="",
    var vehicleRegistrationNumber : String="",
    var vehicleSpeed : Int=0
);

data class RadarResponseDTO(
    var radarId : String ="",
    var name : String="",
    var longitude : Double=0.0,
    var latitude :Double=0.0,
    var altitude : Double=0.0,
    var maxSpeed : Int=0,
    var roadDesignation : String="",
    var radarStatus : RadarStatus=RadarStatus.OFF,
);

data class RadarOverSpeedsDTO(
    var radarId : String ="",
    var name : String="",
    var longitude : Double=0.0,
    var latitude :Double=0.0,
    var altitude : Double=0.0,
    var maxSpeed : Int=0,
    var roadDesignation : String="",
    var radarStatus : RadarStatus=RadarStatus.OFF,
    var overSpeedDetections : List<OverSpeedResponseDTO> = listOf()
);
data class EventDataResponseDTO<T>(
    var type : String="",
    var eventData : T ,
);

data class VehicleRequestDTO(
    val registrationNumber : String,
    val type : VehicleType,
    val brand : String,
    val model : String,
    val fiscalPower : Int,
    val ownerName : String,
    val ownerNationalIdCard : String,
    val ownerEmail : String,
    val ownerPhoneNumber : String,
    val ownerAddress : String
);

data class UpdateVehicleOwnerRequestDTO(
    val registrationNumber : String,
    val ownerName : String,
    val ownerNationalIdCard : String,
    val ownerEmail : String,
    val ownerPhoneNumber : String,
    val ownerAddress : String
);

