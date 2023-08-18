package com.example.myapplication.services

import com.example.myapplication.general_screen.domain.model.PhotoDetails
import com.example.myapplication.main.presentation.general_screen.Photos
import com.example.myapplication.photo_details_screen.domain.model.PhotoStatistics
import javax.inject.Inject


class PhotoApiService @Inject constructor(private var unsplashPhotoApi: UnsplashPhotoApi) {

    suspend fun getPhotos(page: Int, perPage: Int): List<Photos> =
        unsplashPhotoApi.getPhotos(page, perPage)

    suspend fun getPhotoDetails(photoId: String): PhotoDetails? =
        unsplashPhotoApi.getPhotoDetails(photoId)

    suspend fun getPhotoStatistics(photoId: String): PhotoStatistics? =
        unsplashPhotoApi.getPhotoStatistics(photoId)

}