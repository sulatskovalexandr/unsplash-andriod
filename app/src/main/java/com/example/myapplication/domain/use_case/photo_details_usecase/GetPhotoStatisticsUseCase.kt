package com.example.myapplication.domain.use_case.photo_details_usecase

import com.example.myapplication.domain.model.PhotoStatistics
import com.example.myapplication.domain.repository.PhotoDetailsRepository
import javax.inject.Inject

class GetPhotoStatisticsUseCase @Inject constructor(private val photoDetailsRepository: PhotoDetailsRepository) {
    suspend fun execute(photoId: String): PhotoStatistics? =
        photoDetailsRepository.getPhotoStatistics(photoId)
}