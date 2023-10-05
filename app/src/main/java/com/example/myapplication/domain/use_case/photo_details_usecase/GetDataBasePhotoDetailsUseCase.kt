package com.example.myapplication.domain.use_case.photo_details_usecase

import com.example.myapplication.UseCase
import com.example.myapplication.domain.model.Photo
import com.example.myapplication.domain.repository.PhotoDetailsRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class
GetDataBasePhotoDetailsUseCase @Inject constructor(private val photoDetailsRepository: PhotoDetailsRepository) :
    UseCase<String, Photo>(Dispatchers.IO) {

    override suspend fun execute(param: String): Photo =
        photoDetailsRepository.getDataBasePhoto(param)
}