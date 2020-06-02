package com.example.contactsblocker.module.home.cityDetail

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.contactsblocker.AppConstants
import com.example.contactsblocker.BaseViewModel
import com.example.contactsblocker.CitiesDao
import com.example.contactsblocker.ViewModelLifecycleState
import com.example.contactsblocker.model.CityDetail
import com.example.contactsblocker.service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CityDetailViewModel(private val apiService: ApiService) : BaseViewModel() {

    var cityName= MutableLiveData<String>().apply{AppConstants.EMPTY_STRING}
    var provinceName= MutableLiveData<String>().apply{AppConstants.EMPTY_STRING}
    var countryValue= MutableLiveData<String>().apply{AppConstants.EMPTY_STRING}
    var timeZone= MutableLiveData<String>().apply{AppConstants.EMPTY_STRING}
    var population= MutableLiveData<Int>().apply{AppConstants.EMPTY_STRING}
    var isInDB = MutableLiveData<Boolean>().apply { false }
    var isDataAvailable = MutableLiveData<Boolean>().apply { false }
    var dataLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply{false}

    private lateinit var cityDetail : CityDetail

    fun fetchCityDetail(stringExtra: String?) {

    }

    private fun updateView(cityDetail : CityDetail) {

    }

    private fun checkForCityInDB(geonameId: Int?) {

    }

    fun updateDB() {
        if(::cityDetail.isInitialized) {
            if (isInDB.value == true) {
                removeCityFromDB()
            } else {
                addCityInDB()
            }
        }
    }

    private fun addCityInDB() {

    }

    private fun removeCityFromDB() {

    }

    fun closeCityDetailActivity() {
        lifecycleState.onNext(ViewModelLifecycleState.FinishedWithResult)
    }


}