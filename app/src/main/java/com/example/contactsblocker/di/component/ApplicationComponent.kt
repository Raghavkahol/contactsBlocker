package com.example.contactsblocker.di.component

import android.content.Context
import com.example.contactsblocker.CitiesDao
import com.example.contactsblocker.di.module.ApplicationModule
import com.example.contactsblocker.di.qualifier.ApplicationContext
import com.example.contactsblocker.di.scope.ApplicationScoped
import com.example.contactsblocker.service.ApiService
import dagger.Component

@ApplicationScoped
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    @get:ApplicationContext
    val context: Context

    val apiService : ApiService

   // fun getKeepTruckinDataBase(): KeepTruckinDB

   // fun getCitiesDao(): CitiesDao
}