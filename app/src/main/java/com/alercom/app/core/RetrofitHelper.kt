package com.alercom.app.core

import android.annotation.SuppressLint
import android.content.Context
import com.alercom.app.network.AuthInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("StaticFieldLeak")
object RetrofitHelper {
    private var context : Context? = null
    val  BASE_ROOT = "http://3.136.4.86/"
    val  BASE_LOCAL_ROOT = "http://10.0.2.2:4200/"
    val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    val cliente: OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor).addInterceptor( AuthInterceptor(context))
    }.build()

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_LOCAL_ROOT)
            .client(cliente)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}