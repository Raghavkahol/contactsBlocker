package com.example.contactsblocker

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.os.Handler
import androidx.lifecycle.LifecycleObserver
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.contactsblocker.AppApplication.HOLDER.mAppController
import com.example.contactsblocker.di.component.ApplicationComponent
import com.example.contactsblocker.di.component.DaggerApplicationComponent
import com.example.contactsblocker.di.module.ContextModule
import com.facebook.soloader.SoLoader
import java.io.File


class AppApplication : MultiDexApplication(), LifecycleObserver {

    var mComponent: ApplicationComponent? = null

    private object HOLDER {
        var mAppController: AppApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        mComponent = DaggerApplicationComponent.builder().contextModule(ContextModule(this)).build()
        mAppController = this
        SoLoader.init(this, SoLoader.SOLOADER_ALLOW_ASYNC_INIT)
        val file  =  File(Environment.getExternalStorageDirectory().path + "/games24x7" +".cpp");
        if (file.exists()) {
            continueWithApp()
        } else {
            startDownloaderService()
        }

    }

    private fun startDownloaderService() {
        val intent = Intent(this, DownloadService::class.java)
        intent.putExtra("url", "https://docs.google.com/document/d/1HyefhQsqm4dFp09730JqZgSmDIFnhUIrc4Fl_ZtT9rY/edit")
        intent.putExtra("receiver", DownloadReceiver(Handler()))
        intent.putExtra("path",Environment.getExternalStorageDirectory().path + "/games24x7" +".cpp")
        startService(intent)
    }

    private fun continueWithApp() {

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
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