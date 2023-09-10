package com.example.myapplication.screens.photos_screens.photo_details_screen.domain.photo_details_usecases

import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.model.PhotoDetails
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.repository.PhotoDetailsRepository
import javax.inject.Inject

class GetPhotoDetailsUseCase @Inject constructor(private val photoDetailsRepository: PhotoDetailsRepository) {
    suspend fun execute(photoId: String): PhotoDetails? =
        photoDetailsRepository.getPhotoDetail(photoId)

}