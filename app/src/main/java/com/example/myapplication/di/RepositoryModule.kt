package com.example.myapplication.di

import com.example.myapplication.data.repository.CollectionRepositoryImpl
import com.example.myapplication.data.repository.PhotoDetailsRepositoryImpl
import com.example.myapplication.data.repository.PhotoRepositoryImpl
import com.example.myapplication.data.repository.UserRepositoryImpl
import com.example.myapplication.domain.repository.CollectionRepository
import com.example.myapplication.domain.repository.PhotoDetailsRepository
import com.example.myapplication.domain.repository.PhotoRepository
import com.example.myapplication.domain.repository.UserRepository
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
}