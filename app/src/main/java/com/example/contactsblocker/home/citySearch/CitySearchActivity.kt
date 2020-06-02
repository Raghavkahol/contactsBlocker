package com.example.contactsblocker.module.home.citySearch

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsblocker.AppApplication
import com.example.contactsblocker.AppConstants
import com.example.contactsblocker.BaseViewModelActivity
import com.example.contactsblocker.R
import com.example.contactsblocker.di.component.DaggerCitySearchComponent
import com.example.contactsblocker.di.module.CitySearchModule
import com.example.contactsblocker.model.CitySearchResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_city_search.*
import javax.inject.Inject


fun getCitySearchIntent(context: Context): Intent {
    val intent = Intent(context, CitySearchActivity::class.java)
    return intent
}

class CitySearchActivity : BaseViewModelActivity(), View.OnClickListener {
    @Inject
    lateinit var citySearchViewModel: CitySearchViewModel
    lateinit var binding: com.example.contactsblocker.databinding.ActivityCitySearchBinding

    private val REQUEST_CODE_REQUEST_PERMISSION = AppConstants.ONE_INT
    private var locationCallback: LocationCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_city_search)
        setupFragmentComponent()
        initComponents()
        setToolbar()
    }

    override fun onResume() {
        super.onResume()
    }

    fun initComponents() {
        citySearchViewModel.apply {
            binding.viewModel = this
            bindViewModel(this)
        }
        binding.apply{
           viewModel = citySearchViewModel
            lifecycleOwner = this@CitySearchActivity
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = CitySearchAdapter(context,ArrayList<CitySearchResult>())
                itemAnimator = DefaultItemAnimator()
                 addItemDecoration(
                    DividerItemDecoration(
                        context,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        }
        map_view.setOnClickListener(this)
    }

    override fun setupFragmentComponent() {
        DaggerCitySearchComponent.builder()
            .applicationComponent(AppApplication.getInstance()?.mComponent)
            .citySearchModule(CitySearchModule(this))
            .build().inject(this)
    }

    override fun onClick(view : View?) {
        when(view?.id) {
            R.id.map_view -> {
                if (ContextCompat.checkSelfPermission(
                        applicationContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_CODE_REQUEST_PERMISSION
                    )
                } else {
                    getCurrentLocation()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE_REQUEST_PERMISSION && grantResults.size > AppConstants.ZERO_INT) {
            if(grantResults.get(AppConstants.ZERO_INT)== PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                Toast.makeText(this, R.string.label_permission_denied, Toast.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        val locationRequest  = LocationRequest()
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY );
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    if (location != null) {
                        citySearchViewModel.fetchCityListByLocation(location.latitude, location.longitude)
                    }
                }
            }
        }
       LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(
            locationRequest, locationCallback,
            Looper.myLooper()
        )
    }
}