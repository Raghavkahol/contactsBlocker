package com.example.contactsblocker.di.module

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.contactsblocker.ContactsDao
import com.example.contactsblocker.di.scope.ActivityScoped
import com.example.contactsblocker.module.home.HomeViewModel
import com.example.contactsblocker.module.home.HomeViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class HomeModule(private val activity: FragmentActivity) {
    @ActivityScoped
    @Provides
    fun provideHomeViewModel(homeViewModelFactory: HomeViewModelFactory): HomeViewModel {
        return ViewModelProviders.of(activity, homeViewModelFactory).get(HomeViewModel::class.java)
    }

    @ActivityScoped
    @Provides
    fun provideHomeViewModelFactory(contactsDao: ContactsDao): HomeViewModelFactory{
        return HomeViewModelFactory(contactsDao)
    }
}