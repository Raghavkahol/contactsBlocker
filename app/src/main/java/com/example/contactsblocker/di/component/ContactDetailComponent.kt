package com.example.contactsblocker.di.component

import com.example.contactsblocker.di.module.ContactDetailModule
import com.example.contactsblocker.di.scope.ActivityScoped
import com.example.contactsblocker.module.home.cityDetail.ContactDetailActivity
import dagger.Component

@ActivityScoped
@Component(dependencies = [ApplicationComponent::class], modules = [ContactDetailModule::class])
interface ContactDetailComponent {
    fun inject(contactDetailActivity: ContactDetailActivity)
}