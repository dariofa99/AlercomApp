package com.alercom.app.network


import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import com.alercom.app.AlercomApp.Companion.prefs
class AuthInterceptor(context: Context?) : Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var token = prefs.getToken()
        if(request.header("No-Authentication")==null){
              if(!token.isNullOrEmpty())
                {
                    val finalToken =  "Bearer "+ token
                    request = request.newBuilder()
                        .addHeader("Authorization",finalToken)
                        .build()
                }
            }
            return chain.proceed(request)
    }


}