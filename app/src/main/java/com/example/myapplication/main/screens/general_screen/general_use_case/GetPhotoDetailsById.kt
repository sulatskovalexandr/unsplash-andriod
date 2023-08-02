package com.example.myapplication.main.screens.general_screen.general_use_case

import com.example.myapplication.main.data.PhotoApiService
import com.example.myapplication.main.model.PhotoDetails

class GetPhotoDetailsById(private var photoApiService: PhotoApiService) {
    suspend fun execute(photoId: String): PhotoDetails? =
        photoApiService.getPhotoDetails(photoId = photoId)

}