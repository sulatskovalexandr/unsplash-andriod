package com.example.myapplication.photo_details_screen.data.repository

import com.example.myapplication.general_screen.domain.model.PhotoDetails
import com.example.myapplication.photo_details_screen.domain.model.PhotoStatistics
import com.example.myapplication.photo_details_screen.domain.repository.PhotoDetailsRepository
import com.example.myapplication.services.PhotoApiService

class PhotoDetailsRepositoryImpl(private var photoApiService: PhotoApiService) :
    PhotoDetailsRepository {

    override suspend fun getPhotoDetail(photoId: String): PhotoDetails? =
        photoApiService.getPhotoDetails(photoId = photoId)

    override suspend fun getPhotoStatistics(photoId: String): PhotoStatistics? =
        photoApiService.getPhotoStatistics(photoId = photoId)
}