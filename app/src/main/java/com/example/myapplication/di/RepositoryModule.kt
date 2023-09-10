package com.example.myapplication.di

import com.example.myapplication.screens.photos_screens.photo_details_screen.data.repository.PhotoDetailsRepositoryImpl
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.photo_details_usecases.GetPhotoDetailsUseCase
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.photo_details_usecases.GetPhotoStatisticsUseCase
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.repository.PhotoDetailsRepository
import com.example.myapplication.screens.photos_screens.photo_screen.data.repository.PhotoRepositoryImpl
import com.example.myapplication.screens.photos_screens.photo_screen.domain.general_usecases.GetPhotoUseCase
import com.example.myapplication.screens.photos_screens.photo_screen.domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides

@Module
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