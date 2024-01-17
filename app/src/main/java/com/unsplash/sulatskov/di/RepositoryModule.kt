package com.unsplash.sulatskov.di

import com.unsplash.sulatskov.data.repository.*
import com.unsplash.sulatskov.domain.repository.*
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