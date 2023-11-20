package com.example.myapplication.domain.use_case.photo_details_usecase

import com.example.myapplication.UseCase
import com.example.myapplication.domain.model.PhotoDetails
import com.example.myapplication.domain.repository.PhotoDetailsRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetPhotoDetailsUseCase @Inject constructor(private val photoDetailsRepository: PhotoDetailsRepository) :
    UseCase<String, PhotoDetails?>(Dispatchers.IO) {
    override suspend fun execute(photoId: String): PhotoDetails? =
        photoDetailsRepository.getPhotoDetail(photoId)
}