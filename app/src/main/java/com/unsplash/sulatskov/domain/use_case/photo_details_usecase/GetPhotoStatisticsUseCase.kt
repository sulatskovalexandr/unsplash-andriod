package com.unsplash.sulatskov.domain.use_case.photo_details_usecase

import com.unsplash.sulatskov.UseCase
import com.unsplash.sulatskov.domain.model.PhotoStatistics
import com.unsplash.sulatskov.domain.repository.PhotoDetailsRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetPhotoStatisticsUseCase @Inject constructor(private val photoDetailsRepository: PhotoDetailsRepository) :
    UseCase<String, PhotoStatistics?>(Dispatchers.IO) {

    override suspend fun execute(photoId: String): PhotoStatistics? =
        photoDetailsRepository.getPhotoStatistics(photoId)
}