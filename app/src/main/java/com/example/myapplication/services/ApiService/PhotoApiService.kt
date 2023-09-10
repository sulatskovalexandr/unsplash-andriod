package com.example.myapplication.services.ApiService

import com.example.myapplication.general_screen.domain.dto.DownloadPhotoUrl
import com.example.myapplication.general_screen.domain.dto.PhotoDetails
import com.example.myapplication.general_screen.domain.dto.PhotoDto
import com.example.myapplication.photo_details_screen.domain.model.PhotoStatistics
import javax.inject.Inject


class PhotoApiService @Inject constructor(private val unsplashPhotoApi: UnsplashPhotoApi) {

    suspend fun getPhotos(page: Int, perPage: Int): List<PhotoDto> =
        unsplashPhotoApi.getPhotos(page, perPage)

    suspend fun getPhotoDetails(photoId: String): PhotoDetails? =
        unsplashPhotoApi.getPhotoDetails(photoId)

    suspend fun getPhotoStatistics(photoId: String): PhotoStatistics? =
        unsplashPhotoApi.getPhotoStatistics(photoId)

    suspend fun getDownloadPhotoUrl(photoId: String): DownloadPhotoUrl? =
        unsplashPhotoApi.getDownloadPhotoUri(photoId)
}