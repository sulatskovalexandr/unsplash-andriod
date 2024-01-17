package com.unsplash.sulatskov.di

import android.content.Context
import com.unsplash.sulatskov.common.NetworkChecker
import com.unsplash.sulatskov.constants.Const.BASE_URL
import com.unsplash.sulatskov.services.ApiService.PhotoApiService
import com.unsplash.sulatskov.services.ApiService.UnsplashPhotoApi
import com.unsplash.sulatskov.services.photoDownloadService.AndroidPhotoDownloader
import com.unsplash.sulatskov.services.photoDownloadService.PhotoDownloader
import dagger.Module
import dagger.Provides

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
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

    @Provides
    fun providePhotoDownloader(context: Context): PhotoDownloader =
        AndroidPhotoDownloader(context)

    @Provides
    fun provideNetworkConnected(context: Context): NetworkChecker =
        NetworkChecker(context)
}