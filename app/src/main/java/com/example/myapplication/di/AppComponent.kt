package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.MainActivity
import com.example.myapplication.ui.collection_screens.CollectionFragment
import com.example.myapplication.ui.photo_screen.PhotoFragment
import com.example.myapplication.ui.photo_screen.photo_details_screen.PhotoDetailsFragment
import com.example.myapplication.ui.user_screen.UserFragment
import com.example.myapplication.ui.user_screen.user_collection.UserCollectionFragment
import com.example.myapplication.ui.user_screen.users_photo.UserPhotoFragment
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
    fun inject(fragment: UserFragment)
    fun inject(fragment: UserPhotoFragment)
    fun inject(fragment: UserCollectionFragment)
    fun inject(fragment: CollectionFragment)
    fun inject(activity: MainActivity)
}