package com.example.contactsblocker.module.home.citySearch

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
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
import com.example.contactsblocker.databinding.ActivityBlockedContactsBinding
import com.example.contactsblocker.di.component.DaggerCitySearchComponent
import com.example.contactsblocker.di.module.CitySearchModule
import com.example.contactsblocker.model.CitySearchResult
import com.example.contactsblocker.model.Contact
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_blocked_contacts.*
import javax.inject.Inject


fun getCitySearchIntent(context: Context): Intent {
    val intent = Intent(context, CitySearchActivity::class.java)
    return intent
}

class CitySearchActivity : BaseViewModelActivity(), View.OnClickListener{
    @Inject
    lateinit var blockedListViewModel: BlockedListViewModel
    lateinit var binding: ActivityBlockedContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_blocked_contacts)
        setupFragmentComponent()
        initComponents()
        setToolbar()
    }

    override fun onResume() {
        super.onResume()
        blockedListViewModel.fetchCityList()
    }

    fun initComponents() {
        blockedListViewModel.apply {
            binding.viewModel = this
            bindViewModel(this)
        }
        binding.apply{
           viewModel = blockedListViewModel
            lifecycleOwner = this@CitySearchActivity
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = CitySearchAdapter(context,ArrayList<Contact>())
                itemAnimator = DefaultItemAnimator()
                 addItemDecoration(
                    DividerItemDecoration(
                        context,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        }
        label_add.setOnClickListener(this)

    }

    override fun setupFragmentComponent() {
        DaggerCitySearchComponent.builder()
            .applicationComponent(AppApplication.getInstance()?.mComponent)
            .citySearchModule(CitySearchModule(this))
            .build().inject(this)
    }

    override fun onClick(view : View?) {
        val builder : AlertDialog.Builder =  AlertDialog.Builder(this);
        builder.setTitle(R.string.label_add_to_block_list);

        val input : EditText =  EditText(this)
        input.setInputType(InputType.TYPE_CLASS_TEXT)
        input.setHint(R.string.hint_block_number)
        builder.setView(input);
        builder.setPositiveButton(R.string.label_add){dialogInterface, which ->
            blockedListViewModel.addToBlockList(input.getText().toString())
        }

        builder.show();
    }
}