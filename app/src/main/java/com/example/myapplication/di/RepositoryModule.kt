package com.example.myapplication.di

import com.example.myapplication.general_screen.data.repository.PhotoRepositoryImpl
import com.example.myapplication.general_screen.domain.general_usecases.GetPhotoUseCase
import com.example.myapplication.general_screen.domain.repository.PhotoRepository
import com.example.myapplication.photo_details_screen.data.repository.PhotoDetailsRepositoryImpl
import com.example.myapplication.photo_details_screen.domain.photo_details_usecases.GetPhotoDetailsUseCase
import com.example.myapplication.photo_details_screen.domain.photo_details_usecases.GetPhotoStatisticsUseCase
import com.example.myapplication.photo_details_screen.domain.repository.PhotoDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providesPhotoRepository(photoRepository: PhotoRepositoryImpl): PhotoRepository =
        photoRepository

    @Provides
    fun providesPhotoDetailsRepository(photoDetailsRepository: PhotoDetailsRepositoryImpl): PhotoDetailsRepository =
        photoDetailsRepository

    @Provides
    fun provideGetPhotoUseCase(photoRepository: PhotoRepository): GetPhotoUseCase =
        GetPhotoUseCase(photoRepository)

    @Provides
    fun provideGetPhotoDetailsUseCase(photoDetailsRepository: PhotoDetailsRepository): GetPhotoDetailsUseCase =
        GetPhotoDetailsUseCase(photoDetailsRepository)

    @Provides
    fun provideGetPhotoStatisticsUseCase(photoDetailsRepository: PhotoDetailsRepository): GetPhotoStatisticsUseCase =
        GetPhotoStatisticsUseCase(photoDetailsRepository)


}