package com.example.myapplication.services

import com.example.myapplication.constants.Const.BASE_URL
import com.example.myapplication.general_screen.domain.model.PhotoDetails
import com.example.myapplication.main.presentation.general_screen.Photos
import com.example.myapplication.photo_details_screen.domain.model.PhotoStatistics
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PhotoApiService {
    private val retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(UnsplashPhotoApi::class.java)

    suspend fun getPhotos(page: Int, perPage: Int): List<Photos> = service.getPhotos(page, perPage)
    suspend fun getPhotoDetails(photoId: String): PhotoDetails? =
        service.getPhotoDetails(photoId)

    suspend fun getPhotoStatistics(photoId: String): PhotoStatistics? =
        service.getPhotoStatistics(photoId)

}