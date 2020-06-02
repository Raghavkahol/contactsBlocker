package com.example.contactsblocker.di.module

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.contactsblocker.ContactsDao
import com.example.contactsblocker.di.scope.ActivityScoped
import com.example.contactsblocker.module.home.cityDetail.ContactDetailViewModel
import com.example.contactsblocker.module.home.cityDetail.ContactDetailViewModelFactory
import com.example.contactsblocker.service.ApiService
import dagger.Module
import dagger.Provides

@Module
class CityDetailModule(private val activity: FragmentActivity) {
    @ActivityScoped
    @Provides
    fun provideCityDetailViewModel(contactDetailViewModelFactory: ContactDetailViewModelFactory): ContactDetailViewModel {
        return ViewModelProviders.of(activity, contactDetailViewModelFactory).get(ContactDetailViewModel::class.java)
    }

    @ActivityScoped
    @Provides
    fun provideCityDetailViewModelFactory(contactsDao: ContactsDao): ContactDetailViewModelFactory {
        return ContactDetailViewModelFactory(contactsDao);
    }
}