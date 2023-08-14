package com.example.myapplication.general_screen.domain.general_usecases

import com.example.myapplication.general_screen.domain.repository.PhotoRepository
import com.example.myapplication.main.presentation.general_screen.Photos

/**
 * Получить список фотографий
 */
class GetPhotoUseCase(private val photoRepository: PhotoRepository) {
    suspend fun execute(page: Int): List<Photos> = photoRepository.getListPhoto(page)
}
