package com.example.myapplication.domain.use_case.photo_usecase

import com.example.myapplication.UseCase
import com.example.myapplication.domain.model.Photo
import com.example.myapplication.domain.repository.PhotoRepository

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Получить список фотографий из БД
 */

class GetDataBasePhotoUseCase @Inject constructor(private val photoRepository: PhotoRepository) :
    UseCase<ListPhotoParam, List<Photo>>(Dispatchers.IO) {

//    override suspend fun execute(param: Int): List<Photo> =
//        photoRepository.getDataBaseListPhoto(param)

    override suspend fun execute(param: ListPhotoParam): List<Photo> =
        photoRepository.getDataBaseListPhoto(param.page, param.orderBy.value)


}
