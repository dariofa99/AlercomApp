package com.alercom.app.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alercom.app.domain.auth.User

@Entity(tableName = "users_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id              : Int?             = null,
    @ColumnInfo(name = "name") var name            : String?          = null,
    @ColumnInfo(name = "lastname") var lastname        : String?          = null,
    @ColumnInfo(name = "username") var username        : String?          = null,
    @ColumnInfo(name = "email") var email           : String?          = null,
    @ColumnInfo(name = "phone_number") var phoneNumber     : String?          = null,
    @ColumnInfo(name = "address") var address         : String?          = null,
    @ColumnInfo(name = "email_verified_at") var emailVerifiedAt : String?          = null,
    @ColumnInfo(name = "town_id") var townId          : Int?             = null,
    @ColumnInfo(name = "status_id") var statusId        : Int?             = null,
    @ColumnInfo(name = "created_at") var createdAt       : String?          = null,
    @ColumnInfo(name = "updated_at") var updatedAt       : String?          = null,
)
fun User.toDatabase() = UserEntity(
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