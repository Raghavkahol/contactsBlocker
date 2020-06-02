package com.example.contactsblocker

import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BaseViewModelActivity : AppCompatActivity() {

    val compositeDisposable = CompositeDisposable()
    private val viewModelBinder by ViewModelActivityBindingDelegate()
    var mActionBar : ActionBar? = null

    fun bindDisposable(action: () -> Disposable) {
        compositeDisposable.add(action())
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun bindViewModel(viewModel: BaseViewModel) {
        viewModelBinder.bindViewModel(viewModel)
    }

    open fun onViewModelStartWithRequest(state: ViewModelLifecycleState.StartWithRequest) {

    }

    open fun onViewModelFinish() {
        finish()
    }

    open fun setupFragmentComponent() {

    }

    fun setToolbar() {
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        mActionBar = supportActionBar
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    }
