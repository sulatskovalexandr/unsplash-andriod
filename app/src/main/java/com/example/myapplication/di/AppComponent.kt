package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.screens.photos_screens.photo_details_screen.presentation.photo_details_screen.PhotoDetailsFragment
import com.example.myapplication.screens.photos_screens.photo_screen.presentation.photo_screen.PhotoFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [NetworkModule::class, RepositoryModule::class, DbModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(context: Context): Builder

        @BindsInstance
        fun networkModule(apiModule: NetworkModule): Builder

        @BindsInstance
        fun dbModule(interfaceModule: DbModule): Builder

        @BindsInstance
        fun repositoryModule(interfaceModule: RepositoryModule): Builder

        // @BindsInstance   //this two  commented lines can be removed
        // fun contextModule(contextModule: ContextModule): Builder

        // why? because dagger already knows how to provide Context Module

        fun build(): AppComponent
    }

    fun inject(fragment: PhotoFragment)
    fun inject(fragment: PhotoDetailsFragment)
}