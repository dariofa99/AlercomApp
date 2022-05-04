package com.alercom.app.di

import android.content.Context
import androidx.room.Room
import com.alercom.app.data.database.AlercomRoomDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AlercomRoomDatabase::class.java, "alercom_database").build()

   @Singleton
    @Provides
    fun provideQuoteDao(db: AlercomRoomDatabase) = db.getLoginDao()

}