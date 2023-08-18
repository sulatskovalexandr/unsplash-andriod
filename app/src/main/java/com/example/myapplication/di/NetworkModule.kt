package com.example.myapplication.di

import com.example.myapplication.constants.Const.BASE_URL
import com.example.myapplication.services.PhotoApiService
import com.example.myapplication.services.UnsplashPhotoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Provides
    fun provideApi(retrofit: Retrofit): UnsplashPhotoApi =
        retrofit.create(UnsplashPhotoApi::class.java)

    @Provides
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    fun providePhotoApiService(unsplashPhotoApi: UnsplashPhotoApi): PhotoApiService =
        PhotoApiService(unsplashPhotoApi)


}