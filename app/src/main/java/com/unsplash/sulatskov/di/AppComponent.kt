package com.unsplash.sulatskov.di

import android.content.Context
import com.unsplash.sulatskov.domain.model.SearchCollections
import com.unsplash.sulatskov.ui.collection_screens.CollectionFragment
import com.unsplash.sulatskov.ui.collection_screens.collection_details.CollectionDetailsFragment
import com.unsplash.sulatskov.ui.photo_screen.PhotoFragment
import com.unsplash.sulatskov.ui.photo_screen.photo_details_screen.PhotoDetailsFragment
import com.unsplash.sulatskov.ui.user_screen.UserFragment
import com.unsplash.sulatskov.ui.login_screen.LoginFragment
import com.unsplash.sulatskov.ui.search_screen.SearchFragment
import com.unsplash.sulatskov.ui.search_screen.search_collection.SearchCollectionFragment
import com.unsplash.sulatskov.ui.search_screen.search_photo.SearchPhotoFragment
import com.unsplash.sulatskov.ui.search_screen.search_user.SearchUserFragment
import com.unsplash.sulatskov.ui.user_screen.user_collection.UserCollectionFragment
import com.unsplash.sulatskov.ui.user_screen.users_photo.UserPhotoFragment
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
    fun inject(fragment: LoginFragment)
    fun inject(fragment:CollectionDetailsFragment)
    fun inject(fragment:SearchFragment)
    fun inject(fragment:SearchPhotoFragment)
    fun inject(fragment:SearchCollectionFragment)
    fun inject(fragment:SearchUserFragment)
}