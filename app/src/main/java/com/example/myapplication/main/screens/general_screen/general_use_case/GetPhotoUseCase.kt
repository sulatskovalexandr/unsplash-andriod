package com.example.myapplication.main.screens.general_screen.general_use_case

import com.example.myapplication.main.data.PhotoApiService
import com.example.myapplication.main.screens.general_screen.Photos

/**
 * Получить список фотографий
 */
class GetPhotoUseCase(private var photoApiService: PhotoApiService) {

    suspend fun execute(page: Int): List<Photos> =
        photoApiService.getPhotos(page, 20)
}
