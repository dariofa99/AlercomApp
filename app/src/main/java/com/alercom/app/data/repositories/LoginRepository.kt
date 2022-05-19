package com.alercom.app.data.repositories


import com.alercom.app.AlercomApp.Companion.prefs
import com.alercom.app.data.Result
import com.alercom.app.data.database.AlercomRoomDatabase
import com.alercom.app.data.database.dao.LoginDao
import com.alercom.app.data.database.entities.UserEntity
import com.alercom.app.data.services.LoginApiClient
import com.alercom.app.domain.auth.User
import com.alercom.app.network.retrofit
import com.alercom.app.response.ErrorResponse

import com.alercom.app.response.auth.AuthResponse
import com.alercom.app.response.auth.OnAuthResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


import javax.inject.Inject

class LoginRepository {


    var auth: Response<AuthResponse>? = null



    val isLoggedIn: Boolean
        get() = auth != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        auth = null
    }

    fun logout() {
        auth = null
        //dataSource.logout()
    }

    fun login(username: String, password: String,callback : OnAuthResponse) {

        val service = retrofit.create<LoginApiClient>(LoginApiClient::class.java)
        val jo = JsonObject()
        jo.addProperty("username", username)
        jo.addProperty("password", password)
        val call =  service.login(jo)
        call.enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if(response.code() == 200){
                    val auth = response.body()?.authUser!!
                    val us = UserEntity(
                        id = auth?.id,
                        name = auth.name,
                        lastname = auth.lastname,
                        address = auth.address
                    )
                  //  appDatabase.getLoginDao().insertUser(us)
                    response.body()?.accessToken?.let { prefs.saveToken(it) }
                    System.out.println(response.body()?.authUser?.roles?.isEmpty())
                    prefs.saveUserName(response.body()?.authUser?.name)
                    prefs.can(!response.body()?.authUser?.roles?.isEmpty()!!)
                    val resp = Result.Success(response)
                    callback.auth(resp)

                    setLoggedInUser(response)
                }
                if(response.code() == 400  ){
                    val error = ErrorResponse("Error en las credenciales")
                    callback.unautorize(error)
                }
                if(response.code() == 500 ){
                    val error = ErrorResponse("Error en el servidor")
                    callback.unautorize(error)
                }


            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {

                val error = ErrorResponse("Error en el servidor")
                callback.unautorize(error)
            }

        })
    }

    fun loginAnonimus(callback : OnAuthResponse) {

        val service = retrofit.create<LoginApiClient>(LoginApiClient::class.java)
        val call =  service.loginAnonymous()
        call.enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if(response.code() == 200){
                    response.body()?.accessToken?.let { prefs.saveToken(it) }
                    prefs.saveUserName(response.body()?.authUser?.username)
                    val resp = com.alercom.app.data.Result.Success(response)
                    callback.auth(resp)
                    setLoggedInUser(response)
                }
                if(response.code() == 400  ){
                    val error = ErrorResponse("Error en las credenciales")
                    callback.unautorize(error)
                }
                if(response.code() == 500 ){
                    val error = ErrorResponse("Error en el servidor")
                    callback.unautorize(error)
                }


            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {

                val error = ErrorResponse("Error en el servidor")
                callback.unautorize(error)
            }

        })
    }

    private fun setLoggedInUser(authResponse: Response<AuthResponse>) {
        this.auth = authResponse
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }



}