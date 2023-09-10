package com.example.myapplication.services.ApiService

import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.model.DownloadPhotoUrl
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.model.PhotoDetails
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.model.PhotoStatistics
import com.example.myapplication.screens.photos_screens.photo_screen.domain.dto.PhotoDto
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