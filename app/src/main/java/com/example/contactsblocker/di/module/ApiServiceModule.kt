package com.example.contactsblocker.di.module

import com.example.contactsblocker.di.qualifier.ApiServiceQualifier
import com.example.contactsblocker.di.scope.ApplicationScoped
import com.example.contactsblocker.service.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = arrayOf(NetworkModule::class))
class ApiServiceModule {
    fun getBaseUrl(): String {
        return "https://api.teleport.org/api/"
    }

    @Provides
    @ApplicationScoped
    fun crsApiService(@ApiServiceQualifier retrofit: Retrofit): ApiService {
        return retrofit.create<ApiService>(ApiService::class.java)
    }

    @Provides
    @ApplicationScoped
    @ApiServiceQualifier
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(httpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}