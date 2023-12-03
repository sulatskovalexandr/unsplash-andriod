package com.example.myapplication.di

import com.example.myapplication.data.repository.*
import com.example.myapplication.domain.repository.*
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    /**
     * photo
     */

    @Provides
    fun providesPhotoRepository(photoRepository: PhotoRepositoryImpl): PhotoRepository =
        photoRepository

    /**
     * photo_details
     */

    @Provides
    fun providesPhotoDetailsRepository(photoDetailsRepository: PhotoDetailsRepositoryImpl): PhotoDetailsRepository =
        photoDetailsRepository

    /**
     *user
     */

    @Provides
    fun provideUsersPhotoRepository(usersRepository: UserRepositoryImpl): UserRepository =
        usersRepository

    /**
     *collection
     */
    @Provides
    fun provideCollectionPhotoRepository(collectionRepository: CollectionRepositoryImpl): CollectionRepository =
        collectionRepository

    /**
     * login
     */

    @Provides
    fun provideLoginRepository(loginRepository: LoginRepositoryImpl):LoginRepository =
        loginRepository
}