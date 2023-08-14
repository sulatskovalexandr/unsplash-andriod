package com.example.myapplication.photo_details_screen.domain.photo_details_usecases

import com.example.myapplication.general_screen.domain.model.PhotoDetails
import com.example.myapplication.photo_details_screen.domain.repository.PhotoDetailsRepository

class GetPhotoDetailsById(private var photoDetailsRepository: PhotoDetailsRepository) {
    suspend fun execute(photoId: String): PhotoDetails? =
        photoDetailsRepository.getPhotoDetail(photoId)

}