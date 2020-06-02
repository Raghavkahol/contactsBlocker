package com.example.contactsblocker.module.home.citySearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contactsblocker.service.ApiService

class CitySearchViewModelFactory(private var apiService: ApiService) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CitySearchViewModel(apiService) as T
    }
}