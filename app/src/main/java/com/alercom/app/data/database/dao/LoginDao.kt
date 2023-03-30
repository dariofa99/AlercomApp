package com.alercom.app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alercom.app.data.database.entities.UserEntity


@Dao
interface LoginDao {
    @Query("SELECT * from users_table where username = :username  order by id desc")
    fun login(username: String):UserEntity

    @Query("SELECT * from users_table where username = :id  order by id desc")
     fun getById(id: Int):UserEntity

    @Insert(onConflict  = OnConflictStrategy.REPLACE)
     fun insertUser(user:UserEntity)
/*
    @Query("delete from quote_table")
    suspend fun deleteAllQuotes()

   */
}