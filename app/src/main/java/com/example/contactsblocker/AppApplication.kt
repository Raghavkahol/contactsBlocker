package com.example.contactsblocker

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import com.example.contactsblocker.AppApplication.HOLDER.mAppController
import com.example.contactsblocker.di.component.ApplicationComponent
import com.example.contactsblocker.di.component.DaggerApplicationComponent
import com.example.contactsblocker.di.module.ContextModule

class AppApplication : Application(), LifecycleObserver {

    var mComponent: ApplicationComponent? = null

    private object HOLDER {
        var mAppController: AppApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        mComponent = DaggerApplicationComponent.builder().contextModule(ContextModule(this)).build()
        mAppController = this
    }

    companion object {
        fun getInstance(): AppApplication? {

            if (mAppController == null) {
                return null
            }
            return mAppController
        }
    }
}