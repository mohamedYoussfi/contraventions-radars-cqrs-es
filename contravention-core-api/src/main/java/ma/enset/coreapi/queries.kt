package ma.enset.coreapi

class GetAllRadarsQuery();
data class GetRadarById(
    val radarId:String,
);

class GetOverSpeedsQuery();

class SubscribeToEventsQuery();
class GetAllOverSpeedsQuery();
class GetAllOverSpeedsByRegistrationNumberQuery(
    val registrationNumber : String,
);

//======================================//
//========= IMatriculation Center ===== //
//======================================//

class GetAllVehiclesQuery();
class GetAllOwners();
class GetVehicleByRegistrationNumber(
    val registrationNumber : String,
);

class GetAllContraventions();
class GetContraventionsByNationalCardNumber(
    val nationalCardNumber : String
);
