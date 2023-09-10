package com.example.myapplication.photo_details_screen.domain.photo_details_usecases

import com.example.myapplication.general_screen.domain.dto.PhotoDetails
import com.example.myapplication.photo_details_screen.domain.repository.PhotoDetailsRepository
import javax.inject.Inject

class GetPhotoDetailsUseCase @Inject constructor(private val photoDetailsRepository: PhotoDetailsRepository) {
    suspend fun execute(photoId: String): PhotoDetails? =
        photoDetailsRepository.getPhotoDetail(photoId)

}