package com.unsplash.sulatskov.di

import android.content.Context
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ContextModule {

    @Singleton
    @Binds
    abstract fun context(appInstance: Context): Context
}