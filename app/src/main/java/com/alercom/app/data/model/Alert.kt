package com.alercom.app.data.model

import com.google.gson.annotations.SerializedName

data class Alert(

    @SerializedName("id"                          ) var id                        : Int?    = null,
    @SerializedName("event_description"           ) var eventDescription          : String? = null,
    @SerializedName("event_date"                  ) var eventDate                 : String? = null,
    @SerializedName("event_place"                 ) var eventPlace                : String? = null,
    @SerializedName("latitude"                    ) var latitude                  : String? = null,
    @SerializedName("longitude"                   ) var longitude                 : String? = null,
    @SerializedName("event_aditional_information" ) var eventAditionalInformation : String? = null,
    @SerializedName("affected_people"             ) var affectedPeople            : Int?    = null,
    @SerializedName("affected_family"             ) var affectedFamily            : Int?    = null,
    @SerializedName("affected_animals"            ) var affectedAnimals           : Int?    = null,
    @SerializedName("affected_infrastructure"     ) var affectedInfrastructure    : Int?    = null,
    @SerializedName("affected_livelihoods"        ) var affectedLivelihoods       : Int?    = null,
    @SerializedName("event_type_id"               ) var eventTypeId               : Int?    = null,
    @SerializedName("town_id"                     ) var townId                    : Int?    = null,
    @SerializedName("status_id"                   ) var statusId                  : Int?    = null,
    @SerializedName("afectations_range_id"        ) var afectationsRangeId        : Int?    = null,
    @SerializedName("created_at"                  ) var createdAt                 : String? = null,
    @SerializedName("updated_at"                  ) var updatedAt                 : String? = null,
    @SerializedName("town"                        ) var town                      : Town?   = Town(),
    @SerializedName("status"                      ) var status                    : Reference? = Reference(),
    @SerializedName("files"                       ) var files                     : ArrayList<File>? = null,
    @SerializedName("event_type"                  ) var eventType                 : EventType?       = EventType(),
    @SerializedName("affectation_range"           ) var affectationRange          : Reference? = Reference()

)
