package com.example.myapplication.general_screen.domain.general_usecases

import com.example.myapplication.UseCase
import com.example.myapplication.general_screen.domain.model.Photo
import com.example.myapplication.general_screen.domain.repository.PhotoRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Получить список фотографий из БД
 */

class GetDataBasePhotoUseCase @Inject constructor(private val photoRepository: PhotoRepository) :
    UseCase<Int, List<Photo>>(Dispatchers.IO) {

    override suspend fun execute(param: Int): List<Photo> =
        photoRepository.getDataBaseListPhoto(param)
}
