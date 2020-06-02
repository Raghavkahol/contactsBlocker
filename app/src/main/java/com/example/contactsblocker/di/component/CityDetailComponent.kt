package com.example.contactsblocker.di.component

import com.example.contactsblocker.di.module.CityDetailModule
import com.example.contactsblocker.di.scope.ActivityScoped
import com.example.contactsblocker.module.home.cityDetail.CityDetailActivity
import dagger.Component

@ActivityScoped
@Component(dependencies = [ApplicationComponent::class], modules = [CityDetailModule::class])
interface CityDetailComponent {
    fun inject(cityDetailActivity: CityDetailActivity)
}