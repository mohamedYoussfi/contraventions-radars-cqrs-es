package ma.enset.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier

//======================================//
//========= Radar Service============= //
//======================================//

abstract class BaseCommand<T>(
    @TargetAggregateIdentifier open val id : T
);
data class CreateNewRadarCommand(
    override val id : String,
    val payload : RadarDTO,
) : BaseCommand<String>(id);

data class ChangeRadarStatusCommand(
    override val id : String,
    val payload : RadarStatus,
) : BaseCommand<String>(id);

data class ChangeRadarSpeedLimitCommand(
    override val id : String,
    val payload : Int,
) : BaseCommand<String>(id);

data class NewVehicleOverSpeedDetectionCommand(
    override val id : String,
    val payload : OverSpeedRequestDTO,
) : BaseCommand<String>(id);

data class NewContraventionCommand(
    override val id : String,
    val payload : OverSpeedRequestDTO,
) : BaseCommand<String>(id);


//======================================//
//========= IMatriculation Center ===== //
//======================================//

data class CreateVehicleCommand(
    override val id : String,
    val payload : VehicleRequestDTO,
) : BaseCommand<String>(id);

data class UpdateVehicleOwnerCommand(
    override val id : String,
    val payload : UpdateVehicleOwnerRequestDTO,
) : BaseCommand<String>(id);