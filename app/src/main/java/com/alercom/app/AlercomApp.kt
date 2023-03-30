package com.alercom.app

import android.app.Application
import android.content.Intent
import androidx.room.Room
import com.alercom.app.data.database.AlercomRoomDatabase
import com.alercom.app.data.database.entities.UserEntity
import com.alercom.app.network.Prefs


class AlercomApp : Application() {


    companion object {
        lateinit var prefs: Prefs
        //lateinit var room: AlercomRoomDatabase
    }
   val  room = Room
    .databaseBuilder(applicationContext,AlercomRoomDatabase::class.java,"alercom_database")
    .allowMainThreadQueries().build()

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)

        initApp()
    }

      fun initApp() {

        if(prefs.getToken()!="" && prefs.getPrivacyPolicy()){
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }


}