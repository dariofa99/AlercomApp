package com.alercom.app.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.alercom.app.data.database.entities.UserEntity


@Dao
interface LoginDao {
    @Query("SELECT * from users_table where username = :username  order by id desc")
    suspend fun login(username: String):UserEntity

  /*  @Insert(onConflict  = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quotes:List<QuoteEntity>)

    @Query("delete from quote_table")
    suspend fun deleteAllQuotes()

   */
}