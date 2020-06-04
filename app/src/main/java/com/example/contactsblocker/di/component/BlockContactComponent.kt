package com.example.contactsblocker.di.component

import com.example.contactsblocker.di.module.BlockContactModule
import com.example.contactsblocker.di.scope.ActivityScoped
import com.example.contactsblocker.module.home.citySearch.BlockedListActivity

import dagger.Component

@ActivityScoped
@Component(dependencies = [ApplicationComponent::class], modules = [BlockContactModule::class])
interface BlockContactComponent {
    fun inject(blockedListActivity: BlockedListActivity)
}