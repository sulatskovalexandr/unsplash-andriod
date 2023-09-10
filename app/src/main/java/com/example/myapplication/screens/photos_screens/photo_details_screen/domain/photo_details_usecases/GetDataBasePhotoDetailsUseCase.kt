package com.example.myapplication.screens.photos_screens.photo_details_screen.domain.photo_details_usecases

import com.example.myapplication.UseCase
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.repository.PhotoDetailsRepository
import com.example.myapplication.screens.photos_screens.photo_screen.domain.model.Photo
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetDataBasePhotoDetailsUseCase @Inject constructor(private val photoDetailsRepository: PhotoDetailsRepository) :
    UseCase<String, Photo>(Dispatchers.IO) {

    override suspend fun execute(param: String): Photo =
        photoDetailsRepository.getDataBasePhoto(param)
}