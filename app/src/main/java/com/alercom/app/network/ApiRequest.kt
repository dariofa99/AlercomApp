package com.alercom.app.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private var context : Context? = null

val  BASE_ROOT = "http://18.220.203.127/"
val  BASE_LOCAL_ROOT = "http://10.0.2.2:4200/"
val interceptor:HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.BODY
}


val cliente:OkHttpClient = OkHttpClient.Builder().apply {
    this.addInterceptor(interceptor).addInterceptor( AuthInterceptor(context))
}.build()


val retrofit =  Retrofit.Builder()
    .baseUrl(BASE_LOCAL_ROOT)
    .client(cliente)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

/*object RetrofitService{
    val retrofitService:ApiService by lazy { retrofit.create(ApiService::class.java) }
}*/
