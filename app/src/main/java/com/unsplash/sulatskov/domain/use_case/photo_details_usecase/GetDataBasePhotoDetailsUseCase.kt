package com.unsplash.sulatskov.domain.use_case.photo_details_usecase

import com.unsplash.sulatskov.UseCase
import com.unsplash.sulatskov.domain.model.Photo
import com.unsplash.sulatskov.domain.repository.PhotoDetailsRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class
GetDataBasePhotoDetailsUseCase @Inject constructor(private val photoDetailsRepository: PhotoDetailsRepository) :
    UseCase<String, Photo>(Dispatchers.IO) {

    override suspend fun execute(param: String): Photo =
        photoDetailsRepository.getDataBasePhoto(param)
}