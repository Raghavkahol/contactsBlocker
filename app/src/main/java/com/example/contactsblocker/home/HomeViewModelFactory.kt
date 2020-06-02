package com.example.contactsblocker.module.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contactsblocker.CitiesDao
import com.example.contactsblocker.service.ApiService

class HomeViewModelFactory() : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel() as T
    }
}