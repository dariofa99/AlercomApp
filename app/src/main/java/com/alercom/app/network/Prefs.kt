package com.alercom.app.network

import android.content.Context

class Prefs(val context: Context) {
    val SHARED_NAME = "acToken"
    val SHARED_TOKEN = "acToken"
    val SHARED_USERNAME = "userName"

    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun saveToken(token:String){
        storage.edit().putString(SHARED_TOKEN,token).apply()
    }

    fun saveUserName(userName: String?){
        storage.edit().putString(SHARED_USERNAME,userName).apply()
    }

    fun getToken():String{
       return storage.getString(SHARED_TOKEN,"").toString()
    }

    fun getUserName():String{
        return storage.getString(SHARED_USERNAME,"").toString()
    }

    fun wipe(){
        storage.edit().clear().apply()
    }


}

