package com.example.myapplication.photo_details_screen.domain.photo_details_usecases

import com.example.myapplication.UseCase
import com.example.myapplication.photo_details_screen.domain.repository.PhotoDetailsRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class PhotoDownloadUrl @Inject constructor(
    private val photoDetailsRepository: PhotoDetailsRepository,
) : UseCase<String, Unit>(Dispatchers.IO) {

    override suspend fun execute(photoId: String) {
        val photoDownloadUrls = photoDetailsRepository.getDownloadPhotoUrl(photoId)
        photoDownloadUrls.url?.let { photoDetailsRepository.downloadPhoto(photoId, it) }
    }
}