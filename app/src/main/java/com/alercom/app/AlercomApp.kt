package com.alercom.app

import android.app.Application
import android.content.Intent
import com.alercom.app.network.Prefs
import dagger.hilt.android.HiltAndroidApp

class AlercomApp : Application() {
    companion object {
        lateinit var prefs: Prefs
    }
    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
        initApp()
    }

    fun initApp() {

        //  prefs.wipe()
        if( !prefs.getToken().isEmpty()){
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}