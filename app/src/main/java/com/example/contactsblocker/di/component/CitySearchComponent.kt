package com.example.contactsblocker.di.component

import com.example.contactsblocker.di.module.CitySearchModule
import com.example.contactsblocker.di.scope.ActivityScoped
import com.example.contactsblocker.module.home.citySearch.CitySearchActivity
import dagger.Component

@ActivityScoped
@Component(dependencies = [ApplicationComponent::class], modules = [CitySearchModule::class])
interface CitySearchComponent {
    fun inject(citySearchActivity: CitySearchActivity)
}