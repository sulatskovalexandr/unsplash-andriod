package com.example.myapplication.general_screen.data.repository

import com.example.myapplication.general_screen.domain.repository.PhotoRepository
import com.example.myapplication.main.presentation.general_screen.Photos
import com.example.myapplication.services.PhotoApiService
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService,
) : PhotoRepository {

    override suspend fun getListPhoto(page: Int): List<Photos> =
        photoApiService.getPhotos(page = page, 20)

}