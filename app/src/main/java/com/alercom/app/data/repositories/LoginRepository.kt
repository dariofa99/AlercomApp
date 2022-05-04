package com.alercom.app.data.repositories


import com.alercom.app.data.database.dao.LoginDao
import com.alercom.app.data.services.LoginService

import com.alercom.app.response.auth.AuthResponse


import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api : LoginService,
    private  val loginDao: LoginDao
) {


    suspend fun login(pass: String, username: String): AuthResponse {
        return api.login(username, pass)
    }

    /*
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
        val call =  service.loginAnonimus()
        call.enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if(response.code() == 200){
                    response.body()?.accessToken?.let { prefs.saveToken(it) }
                    prefs.saveUserName(response.body()?.authUser?.username)
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

    private fun setLoggedInUser(authResponse: Response<AuthResponse>) {
        this.auth = authResponse
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
*/
}