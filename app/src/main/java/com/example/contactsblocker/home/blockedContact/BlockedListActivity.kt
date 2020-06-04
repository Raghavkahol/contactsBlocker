package com.example.contactsblocker.module.home.citySearch

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsblocker.AppApplication
import com.example.contactsblocker.AppConstants
import com.example.contactsblocker.BaseViewModelActivity
import com.example.contactsblocker.R
import com.example.contactsblocker.databinding.ActivityBlockedContactsBinding
import com.example.contactsblocker.di.component.DaggerBlockContactComponent
import com.example.contactsblocker.di.module.BlockContactModule
import com.example.contactsblocker.model.Contact
import kotlinx.android.synthetic.main.activity_blocked_contacts.*
import javax.inject.Inject


fun getCitySearchIntent(context: Context): Intent {
    val intent = Intent(context, BlockedListActivity::class.java)
    return intent
}

class BlockedListActivity : BaseViewModelActivity(), View.OnClickListener{
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
            lifecycleOwner = this@BlockedListActivity
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
        DaggerBlockContactComponent.builder()
            .applicationComponent(AppApplication.getInstance()?.mComponent)
            .blockContactModule(BlockContactModule(this))
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
            if(input.getText().length == AppConstants.NUMBER_SIZE) {
                blockedListViewModel.addToBlockList(input.getText().toString())
            } else {
                Toast.makeText(this, " Please enter a valid 10 digit number", Toast.LENGTH_LONG).show()
            }
        }

        builder.show();
    }
}