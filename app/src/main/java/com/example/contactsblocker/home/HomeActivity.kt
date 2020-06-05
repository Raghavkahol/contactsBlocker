package com.example.contactsblocker.module.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsblocker.*
import com.example.contactsblocker.di.component.DaggerHomeComponent
import com.example.contactsblocker.di.module.HomeModule
import com.example.contactsblocker.model.Contact
import com.example.contactsblocker.module.home.citySearch.getCitySearchIntent
import javax.inject.Inject


class HomeActivity : BaseViewModelActivity() {
    @Inject
    lateinit var homeViewModel: HomeViewModel
    lateinit var binding: com.example.contactsblocker.databinding.ActivityHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        setupFragmentComponent()
        initComponents()
        checkForPermissions()
    }


    fun checkForPermissions() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)
            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)
            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ANSWER_PHONE_CALLS)
            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALL_LOG)
            == PackageManager.PERMISSION_GRANTED) {
            homeViewModel.getAllContacts(contentResolver);
        } else {
            requestPermission();
        }
    }



    fun requestPermission() {
        if (ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_DENIED ||  ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_DENIED
            ||  ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_DENIED
            ||  ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.ANSWER_PHONE_CALLS) != PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf<String>(android.Manifest.permission.READ_CONTACTS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG, Manifest.permission.ANSWER_PHONE_CALLS), 1)
        } else {
            homeViewModel.getAllContacts(contentResolver)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1 && grantResults.size > AppConstants.ZERO_INT) {
            if(grantResults.get(AppConstants.ZERO_INT)== PackageManager.PERMISSION_GRANTED) {
                homeViewModel.getAllContacts(contentResolver)
            } else {
                Toast.makeText(this, R.string.label_permission_denied, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun initComponents() {
        getSupportActionBar()?.setDisplayShowTitleEnabled(false);
        homeViewModel.apply {
            binding.viewModel = this
            bindViewModel(this)
        }
        binding.apply{
            viewModel = homeViewModel
            lifecycleOwner = this@HomeActivity
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = HomeAdapter(context, ArrayList<Contact>())
                itemAnimator = DefaultItemAnimator()
                addItemDecoration(DividerItemDecoration(this@HomeActivity, LinearLayoutManager.VERTICAL))
            }

        }
    }
    override fun setupFragmentComponent() {
        DaggerHomeComponent.builder()
            .applicationComponent(AppApplication.getInstance()?.mComponent)
            .homeModule(HomeModule(this))
            .build().inject(this)
    }

    override fun onViewModelStartWithRequest(state: ViewModelLifecycleState.StartWithRequest) {
        when (state.request) {
            AppConstants.ONE_INT -> {
                startActivity(getCitySearchIntent(this))
            }
        }
    }
}
