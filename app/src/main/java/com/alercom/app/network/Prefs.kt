package com.alercom.app.network

import android.content.Context
import com.alercom.app.response.auth.AuthUser

class Prefs(val context: Context) {
    val SHARED_NAME = "acToken"
    val SHARED_TOKEN = "acToken"
    val SHARED_CAN = "userCan"
    val SHARED_USERNAME = "userName"
    val SHARED_AUTH :AuthUser? = null

    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun saveToken(token:String){
        storage.edit().putString(SHARED_TOKEN,token).apply()
    }

    fun can(can:Boolean){
        storage.edit().putBoolean(SHARED_CAN,can).apply()
    }

    fun saveUserName(userName: String?){
        storage.edit().putString(SHARED_USERNAME,userName).apply()
    }

    fun getToken():String{
       return storage.getString(SHARED_TOKEN,"").toString()
    }
    fun getCan():Boolean{
        return storage.getBoolean(SHARED_CAN,false)
    }
    fun getUserName():String{
        return storage.getString(SHARED_USERNAME,"").toString()
    }

    fun wipe(){
        storage.edit().clear().apply()
    }

   /* fun saveAuth(auth: AuthUser?){
        //storage.edit().putString(SHARED_AUTH,auth).apply()
    }*/

}

