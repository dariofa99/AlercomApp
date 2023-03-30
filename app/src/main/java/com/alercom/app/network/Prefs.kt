package com.alercom.app.network

import android.content.Context
import com.alercom.app.response.auth.AuthUser

class Prefs(val context: Context) {
    val SHARED_NAME = "acToken"
    val SHARED_TOKEN = "acToken"
    val CANCHANGESTATUS = "userCan"
    val SHARED_USERNAME = "userName"
    val ACCEPTED_PRIVACY_POLICY = "accepted_privacy_policy"

    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun saveToken(token:String){
        storage.edit().putString(SHARED_TOKEN,token).apply()
    }

    fun canChangeStatus(can:Boolean){
        storage.edit().putBoolean(CANCHANGESTATUS,can).apply()
    }

    fun saveUserName(userName: String?){
        storage.edit().putString(SHARED_USERNAME,userName).apply()
    }

    fun getToken():String{
       return storage.getString(SHARED_TOKEN,"").toString()
    }
    fun canChangeStatus():Boolean{
        return storage.getBoolean(CANCHANGESTATUS,false)
    }
    fun getUserName():String{
        return storage.getString(SHARED_USERNAME,"").toString()
    }

    fun wipe(){
        storage.edit().clear().apply()
    }

    fun setPrivacyPolicy(auth: Boolean){
        storage.edit().putBoolean(ACCEPTED_PRIVACY_POLICY,auth).apply()
    }

    fun getPrivacyPolicy() : Boolean{
        return storage.getBoolean(ACCEPTED_PRIVACY_POLICY,false)
    }

}

