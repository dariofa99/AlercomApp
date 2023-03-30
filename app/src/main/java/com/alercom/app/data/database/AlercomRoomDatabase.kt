package com.alercom.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alercom.app.data.database.dao.LoginDao
import com.alercom.app.data.database.dao.UserDao
import com.alercom.app.data.database.entities.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class AlercomRoomDatabase : RoomDatabase() {
    abstract fun getLoginDao():LoginDao
    abstract fun getUserDao():UserDao

}

