package com.example.contactsblocker.di.module

import android.content.Context
import com.example.contactsblocker.di.qualifier.ApplicationContext
import com.example.contactsblocker.di.scope.ApplicationScoped
import dagger.Module
import dagger.Provides

@Module
class ContextModule(context: Context) {

    private var context: Context

    init {
        this.context = context
    }

    @Provides
    @ApplicationContext
    @ApplicationScoped
    fun context(): Context {
        return this.context
    }
}