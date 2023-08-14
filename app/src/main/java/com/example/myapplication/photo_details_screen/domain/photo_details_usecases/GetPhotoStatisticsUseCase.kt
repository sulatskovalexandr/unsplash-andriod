package com.example.myapplication.photo_details_screen.domain.photo_details_usecases

import com.example.myapplication.photo_details_screen.domain.model.PhotoStatistics
import com.example.myapplication.photo_details_screen.domain.repository.PhotoDetailsRepository

class GetPhotoStatisticsUseCase(private var photoDetailsRepository: PhotoDetailsRepository) {
    suspend fun execute(photoId: String): PhotoStatistics? =
        photoDetailsRepository.getPhotoStatistics(photoId)
}