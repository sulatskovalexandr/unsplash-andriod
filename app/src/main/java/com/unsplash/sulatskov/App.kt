package com.unsplash.sulatskov

import android.app.Application
import com.unsplash.sulatskov.di.*

lateinit var appComponent: AppComponent
    private set

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .dbModule(DbModule())
            .networkModule(NetworkModule())
            .repositoryModule(RepositoryModule())
            .build()
    }
}
