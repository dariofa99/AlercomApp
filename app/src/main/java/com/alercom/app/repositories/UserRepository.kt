package com.alercom.app.repositories


import com.alercom.app.response.user.OnUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.alercom.app.network.retrofit
import com.alercom.app.request.CreateUserRequest
import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.user.UserResponse
import com.alercom.app.services.UserService

class UserRepository {


    fun getAuthUser(callback : OnUserResponse) {

        val service = retrofit.create<UserService>(UserService::class.java)
        val call =  service.getUser()
        call.enqueue(object: Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.code() == 200){
                    //val resp = Result.Success(response)
                    val user = response.body()?.user
                    callback.success(user)

                }
                if(response.code() == 403){
                  //  val error = ErrorResponse("Token no existe")
                    //val resp = Result.Error(error)
                 //   callback.unautorize(error)

                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {


            }

        })

    }

   fun update(user:CreateUserRequest,id:Int, callback : OnUserResponse) {

        val service = retrofit.create<UserService>(UserService::class.java)
        val call =  service.update(id,user)
        call.enqueue(object: Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.code() == 200){
                    val user = response.body()?.user
                    callback.success(user)

                }

                if(response.code() == 201){
                    callback.errors(response.body()?.errors)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {

            }


        })

    }

    fun store(user:CreateUserRequest, callback : OnUserResponse) {

        val service = retrofit.create<UserService>(UserService::class.java)
        val call =  service.store(user)
        call.enqueue(object: Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.code() == 200){
                    val user = response.body()?.user
                    callback.success(user)
                }
                if(response.code() == 201){
                    callback.errors(response.body()?.errors)
                }
                if(response.code() == 500 ){
                    val error = ErrorResponse("Error en el servidor")
                    callback.error(error)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {

            }


        })

    }

}


