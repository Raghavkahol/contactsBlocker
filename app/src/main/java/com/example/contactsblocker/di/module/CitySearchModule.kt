package com.example.contactsblocker.di.module

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.contactsblocker.ContactsDao
import com.example.contactsblocker.di.scope.ActivityScoped
import com.example.contactsblocker.module.home.citySearch.BlockedListViewModel
import com.example.contactsblocker.module.home.citySearch.BlockedListViewModelFactory
import com.example.contactsblocker.service.ApiService
import dagger.Module
import dagger.Provides

@Module
class CitySearchModule(private val activity: FragmentActivity) {
    @ActivityScoped
    @Provides
    fun provideCitySearchViewModel(blockedListViewModelFactory: BlockedListViewModelFactory): BlockedListViewModel {
        return ViewModelProviders.of(activity, blockedListViewModelFactory).get(BlockedListViewModel::class.java)
    }

    @ActivityScoped
    @Provides
    fun provideCitySearchViewModelFactory(contactsDao: ContactsDao): BlockedListViewModelFactory{
        return BlockedListViewModelFactory(contactsDao);
    }
}