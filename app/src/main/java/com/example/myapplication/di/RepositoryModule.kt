package com.example.myapplication.di

import com.example.myapplication.data.repository.CollectionRepositoryImpl
import com.example.myapplication.data.repository.PhotoDetailsRepositoryImpl
import com.example.myapplication.data.repository.PhotoRepositoryImpl
import com.example.myapplication.data.repository.UserRepositoryImpl
import com.example.myapplication.domain.repository.CollectionRepository
import com.example.myapplication.domain.repository.PhotoDetailsRepository
import com.example.myapplication.domain.repository.PhotoRepository
import com.example.myapplication.domain.repository.UserRepository
import com.example.myapplication.domain.use_case.collection_usecase.GetCollectionUseCase
import com.example.myapplication.domain.use_case.photo_details_usecase.GetPhotoDetailsUseCase
import com.example.myapplication.domain.use_case.photo_details_usecase.GetPhotoStatisticsUseCase
import com.example.myapplication.domain.use_case.photo_usecase.GetPhotoUseCase
import com.example.myapplication.domain.use_case.user_usecase.GetUserCollectionUseCase
import com.example.myapplication.domain.use_case.user_usecase.GetUserPhotoUseCase
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

    @Provides
    fun provideGetPhotoUseCase(photoRepository: PhotoRepository): GetPhotoUseCase =
        GetPhotoUseCase(photoRepository)

    /**
     * photo_details
     */

    @Provides
    fun providesPhotoDetailsRepository(photoDetailsRepository: PhotoDetailsRepositoryImpl): PhotoDetailsRepository =
        photoDetailsRepository

    @Provides
    fun provideGetPhotoDetailsUseCase(photoDetailsRepository: PhotoDetailsRepository): GetPhotoDetailsUseCase =
        GetPhotoDetailsUseCase(photoDetailsRepository)

    @Provides
    fun provideGetPhotoStatisticsUseCase(photoDetailsRepository: PhotoDetailsRepository): GetPhotoStatisticsUseCase =
        GetPhotoStatisticsUseCase(photoDetailsRepository)

    /**
     *user
     */

    @Provides
    fun provideUsersPhotoRepository(usersRepository: UserRepositoryImpl): UserRepository =
        usersRepository

    @Provides
    fun provideGetUsersPhotoUseCase(usersRepository: UserRepository): GetUserPhotoUseCase =
        GetUserPhotoUseCase((usersRepository))

    @Provides
    fun provideGetUsersCollectionUseCase(usersRepository: UserRepository): GetUserCollectionUseCase =
        GetUserCollectionUseCase((usersRepository))

    /**
     *collection
     */
    @Provides
    fun provideCollectionPhotoRepository(collectionRepository: CollectionRepositoryImpl): CollectionRepository =
        collectionRepository

    @Provides
    fun provideGetCollectionUseCase(collectionRepository: CollectionRepository): GetCollectionUseCase =
        GetCollectionUseCase(collectionRepository)


}