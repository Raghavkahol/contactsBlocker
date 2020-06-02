package com.example.contactsblocker.di.module

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.contactsblocker.di.scope.ActivityScoped
import com.example.contactsblocker.module.home.cityDetail.CityDetailViewModel
import com.example.contactsblocker.module.home.cityDetail.CityDetailViewModelFactory
import com.example.contactsblocker.module.home.citySearch.CitySearchViewModel
import com.example.contactsblocker.module.home.citySearch.CitySearchViewModelFactory
import com.example.contactsblocker.service.ApiService
import dagger.Module
import dagger.Provides

@Module
class CitySearchModule(private val activity: FragmentActivity) {
    @ActivityScoped
    @Provides
    fun provideCitySearchViewModel(citySearchViewModelFactory: CitySearchViewModelFactory): CitySearchViewModel {
        return ViewModelProviders.of(activity, citySearchViewModelFactory).get(CitySearchViewModel::class.java)
    }

    @ActivityScoped
    @Provides
    fun provideCitySearchViewModelFactory(apiService: ApiService): CitySearchViewModelFactory{
        return CitySearchViewModelFactory(apiService);
    }
}