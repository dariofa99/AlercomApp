package com.alercom.app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alercom.app.data.database.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * from users_table order by id desc")
    fun getAll(): UserEntity

    @Query("SELECT * from users_table where username = :id  order by id desc")
    fun getById(id: Int): UserEntity

    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    fun store(user: UserEntity)
}