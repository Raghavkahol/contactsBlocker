package com.example.contactsblocker.module.home.cityDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.contactsblocker.AppApplication
import com.example.contactsblocker.AppConstants
import com.example.contactsblocker.BaseViewModelActivity
import com.example.contactsblocker.R
import com.example.contactsblocker.databinding.ActivityContactDetailsBinding
import com.example.contactsblocker.di.component.DaggerContactDetailComponent
import com.example.contactsblocker.di.module.ContactDetailModule
import javax.inject.Inject

fun getContactDetailActivity(context: Context, cityName : Int?): Intent {
    val intent  = Intent(context, ContactDetailActivity::class.java)
    intent.putExtra(AppConstants.ID, cityName)
    return intent
}

class ContactDetailActivity : BaseViewModelActivity() {
    @Inject
    lateinit var contactDetailViewModel: ContactDetailViewModel
    lateinit var binding: ActivityContactDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_details)
        setupFragmentComponent()
        initComponents()
        setToolbar()
        contactDetailViewModel.fetchContactDetail(intent.getIntExtra(AppConstants.ID, 1))
    }

    fun initComponents() {
        contactDetailViewModel.apply {
            binding.viewModel = this
            bindViewModel(this)
        }
       binding.apply {
           viewModel = contactDetailViewModel
           lifecycleOwner = this@ContactDetailActivity
       }
    }

    override fun setupFragmentComponent() {
        DaggerContactDetailComponent.builder()
            .applicationComponent(AppApplication.getInstance()?.mComponent)
            .contactDetailModule(ContactDetailModule(this))
            .build().inject(this)
    }
}