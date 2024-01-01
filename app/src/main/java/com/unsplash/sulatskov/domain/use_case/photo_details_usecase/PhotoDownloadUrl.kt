package com.unsplash.sulatskov.domain.use_case.photo_details_usecase

import com.unsplash.sulatskov.UseCase
import com.unsplash.sulatskov.domain.repository.PhotoDetailsRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class PhotoDownloadUrl @Inject constructor(
    private val photoDetailsRepository: PhotoDetailsRepository,
) : UseCase<String, Unit>(Dispatchers.IO) {
    override suspend fun execute(param: String) {
        val photoDownloadUrls = photoDetailsRepository.getDownloadPhotoUrl(param)
        photoDownloadUrls.url?.let {
            photoDetailsRepository.downloadPhoto(param, it)
        }
    }
}