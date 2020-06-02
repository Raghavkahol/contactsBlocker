package com.example.contactsblocker.module.home.cityDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contactsblocker.CitiesDao
import com.example.contactsblocker.service.ApiService

class CityDetailViewModelFactory(private var apiService: ApiService) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CityDetailViewModel(apiService) as T
    }
}