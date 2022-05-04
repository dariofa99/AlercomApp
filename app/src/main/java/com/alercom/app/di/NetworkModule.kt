package com.alercom.app.di


import com.alercom.app.core.RetrofitHelper
import com.alercom.app.data.services.LoginApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return RetrofitHelper.getRetrofit()
    }

    @Singleton
    @Provides
    fun provideLoginApiClient(retrofit: Retrofit):LoginApiClient{
        return retrofit.create(LoginApiClient::class.java)
    }
}