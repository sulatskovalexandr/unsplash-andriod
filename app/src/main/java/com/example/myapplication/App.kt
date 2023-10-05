package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.*

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
