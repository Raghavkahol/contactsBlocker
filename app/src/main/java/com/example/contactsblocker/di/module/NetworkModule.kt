package com.example.contactsblocker.di.module

import com.example.contactsblocker.BuildConfig
import com.example.contactsblocker.di.scope.ApplicationScoped
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module(includes = arrayOf(ContextModule::class))
class NetworkModule {

    @Provides
    @ApplicationScoped
    fun okHttpClient(
        logging: HttpLoggingInterceptor
    ): OkHttpClient {

        val httpClientBuilder = OkHttpClient.Builder();

        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            if (!httpClientBuilder.interceptors().contains(logging)) {
                httpClientBuilder.addInterceptor(logging)
            }
        }
        return httpClientBuilder.build()
    }

    @Provides
    @ApplicationScoped
    internal fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
    }

}