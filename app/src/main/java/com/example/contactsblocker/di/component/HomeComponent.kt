package com.example.contactsblocker.di.component

import com.example.contactsblocker.di.module.HomeModule
import com.example.contactsblocker.di.scope.ActivityScoped
import com.example.contactsblocker.module.home.HomeActivity
import dagger.Component

@ActivityScoped
@Component(dependencies = [ApplicationComponent::class], modules = [HomeModule::class])
interface HomeComponent {
    fun inject(homeActivity: HomeActivity)
}