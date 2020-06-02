package com.example.contactsblocker.di.module

import android.content.Context
import androidx.room.Room
import com.example.contactsblocker.contactsDB
import com.example.contactsblocker.di.qualifier.ApplicationContext
import com.example.contactsblocker.di.scope.ApplicationScoped
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(ContextModule::class, ApiServiceModule::class))
class ApplicationModule {
    @Provides
    @ApplicationScoped
    internal fun provideDataBase(@ApplicationContext context: Context): contactsDB {
        return Room.databaseBuilder(context, contactsDB::class.java, "contactsblocker.db")
            .fallbackToDestructiveMigration()
            .build()
    }
//
//    @Provides
//    @ApplicationScoped
//    internal fun provideCitiesDao(db: KeepTruckinDB): CitiesDao {
//       // return db.citiesDao
//    }
}