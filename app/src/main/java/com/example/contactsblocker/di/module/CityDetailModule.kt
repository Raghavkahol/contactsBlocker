package com.example.contactsblocker.di.module

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.contactsblocker.CitiesDao
import com.example.contactsblocker.di.scope.ActivityScoped
import com.example.contactsblocker.module.home.cityDetail.CityDetailViewModel
import com.example.contactsblocker.module.home.cityDetail.CityDetailViewModelFactory
import com.example.contactsblocker.service.ApiService
import dagger.Module
import dagger.Provides

@Module
class CityDetailModule(private val activity: FragmentActivity) {
    @ActivityScoped
    @Provides
    fun provideCityDetailViewModel(cityDetailViewModelFactory: CityDetailViewModelFactory): CityDetailViewModel {
        return ViewModelProviders.of(activity, cityDetailViewModelFactory).get(CityDetailViewModel::class.java)
    }

    @ActivityScoped
    @Provides
    fun provideCityDetailViewModelFactory(apiService: ApiService): CityDetailViewModelFactory {
        return CityDetailViewModelFactory(apiService);
    }
}