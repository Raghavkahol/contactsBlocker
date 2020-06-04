package com.example.contactsblocker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.contactsblocker.di.component.DaggerApplicationComponent
import com.example.contactsblocker.di.module.ApplicationModule
import com.example.contactsblocker.di.module.ContextModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class IncomingCalReceiver : BroadcastReceiver() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onReceive(context : Context, intent : Intent?) {
        val component = DaggerApplicationComponent.builder().contextModule(ContextModule(context)).applicationModule(ApplicationModule()).build()
        if (TelephonyManager.ACTION_PHONE_STATE_CHANGED != intent?.action) {
            return
        }
        val newState = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        if (TelephonyManager.EXTRA_STATE_RINGING == newState) {
            val phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
            if (phoneNumber == null) {
                return
            }
            val blockedNumberDao: ContactsDao = component.getContactsDao()
            bindDisposable {
                blockedNumberDao.getBlockedContactListObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                            it.forEach {
                                it.number?.trim()
                                it.number = it.number?.replace("\\s".toRegex(), "")
                                if(it.number == phoneNumber) {
                                val telecomManager = context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
                                telecomManager.endCall()
                                Toast.makeText(context, "Call from Blocked number $phoneNumber rejected", Toast.LENGTH_LONG).show()
                            }
                            }

                    }
            }
        }
    }
    fun bindDisposable(action: () -> Disposable) {
        compositeDisposable.add(action())
    }

}