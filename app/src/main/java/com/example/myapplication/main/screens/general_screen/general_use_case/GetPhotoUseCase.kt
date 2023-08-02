package com.example.myapplication.main.screens.general_screen.general_use_case

import com.example.myapplication.main.data.PhotoApiService
import com.example.myapplication.main.screens.general_screen.Photos

class GetPhotoUseCase(private var PhotoApiService: PhotoApiService) {

    suspend fun execute(): List<Photos> {
        return PhotoApiService.getPhotos(1, 50)

    }


}