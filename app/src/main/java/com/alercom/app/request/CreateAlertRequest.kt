package com.alercom.app.request

import com.google.gson.annotations.SerializedName

data class CreateAlertRequest(
    @SerializedName("event_description"           ) val eventDescription: String? = null,
    @SerializedName("event_date"                  ) val eventDate: String?  = null,
    @SerializedName("event_place"                 ) val eventPlace: String?  = null,
    @SerializedName("event_aditional_information" ) val eventAditionalInformation: String?  = null,
    @SerializedName("affected_people"             ) val affectedPeople: Boolean? = null,
    @SerializedName("affected_family"             ) val affectedFamily: Boolean? = null,
    @SerializedName("affected_animals"            ) val affectedAnimals: Boolean? = null,
    @SerializedName("affected_infrastructure"     ) val affectedInfrastructure: Boolean?     = null,
    @SerializedName("affected_livelihoods"        ) val affectedLivelihoods: Boolean?     = null,
    @SerializedName("affected_enviroment"        ) val affectedEnviroment: Boolean?     = null,
    @SerializedName("event_type_id"               ) val eventTypeId: Int?     = null,
    @SerializedName("town_id"                     ) val townId: Int?     = null,
    @SerializedName("status_id"                   ) val statusId: Int?     = null,
    @SerializedName("afectations_range_id"        ) val afectationsRangeId: Int?     = null,
    @SerializedName("latitude"                )     val latitude: Double?     = null,
    @SerializedName("longitude"               )     val longitude: Double?     = null
)
