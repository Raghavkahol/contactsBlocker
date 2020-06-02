package com.example.contactsblocker.module.home.citySearch

import android.text.TextUtils
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.example.contactsblocker.AppConstants
import com.example.contactsblocker.BaseViewModel
import com.example.contactsblocker.model.Cities
import com.example.contactsblocker.model.CitySearchResult
import com.example.contactsblocker.service.ApiService

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CitySearchViewModel(private val apiService: ApiService) : BaseViewModel() {

    var cityName= MutableLiveData<String>().apply { AppConstants.EMPTY_STRING }
    var cities  = ObservableArrayList<CitySearchResult>()
    var isDataUnavalable = MutableLiveData<Boolean>().apply { false }

    var dataLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply{ false }

    fun fetchCityList() {

    }

    fun fetchCityListByLocation( latitude : Double, longitude : Double) {

    }

    fun updateData(city : Cities) {

    }
}