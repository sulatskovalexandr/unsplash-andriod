package com.unsplash.sulatskov.domain.use_case.photo_usecase

import com.unsplash.sulatskov.UseCase
import com.unsplash.sulatskov.domain.model.Photo
import com.unsplash.sulatskov.domain.repository.PhotoRepository

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
