package com.alercom.app.domain.auth



import com.alercom.app.data.database.entities.UserEntity
import com.alercom.app.data.model.User

import com.google.gson.annotations.SerializedName


data class User(
    var id              : Int?              = null,
    var name            : String?           = null,
    var lastname        : String?           = null,
    var username        : String?           = null,
    var email           : String?           = null,
    var phoneNumber        : String?           = null,
    var emailVerifiedAt : String?          = null,
    var address : String?           = null,
    var townId          : Int?             = null,
    var statusId          : Int?             = null,
    var createdAt       : String?           = null,
    var updatedAt       : String?           = null,
         //  @SerializedName("permissions"       ) var permissions     : ArrayList<String> = arrayListOf(),
         //  @SerializedName("roles"             ) var roles           : ArrayList<Roles>  = arrayListOf()
    )
fun User.toDomain() = com.alercom.app.domain.auth.User(
    id = id,
    name = name,
    lastname = lastname,
    username = username,
    email = email,
    phoneNumber = phoneNumber,
    address = address,
    emailVerifiedAt = emailVerifiedAt,
    townId = townId,
    statusId = statusId,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun UserEntity.toDomain() = com.alercom.app.domain.auth.User(
    id = id,
    name = name,
    lastname = lastname,
    username = username,
    email = email,
    phoneNumber = phoneNumber,
    address = address,
    emailVerifiedAt = emailVerifiedAt,
    townId = townId,
    statusId = statusId,
    createdAt = createdAt,
    updatedAt = updatedAt
)