package com.unsplash.sulatskov.domain.use_case.photo_usecase

import com.unsplash.sulatskov.UseCase
import com.unsplash.sulatskov.domain.model.Photo
import com.unsplash.sulatskov.domain.repository.PhotoRepository
import com.unsplash.sulatskov.ui.photo_screen.OrderListPhoto
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

data class ListPhotoParam(
    var page: Int,
    val orderBy: OrderListPhoto
)

/**
 * Получить список фотографий
 */
class GetPhotoUseCase @Inject constructor(private val photoRepository: PhotoRepository) :
    UseCase<ListPhotoParam, List<Photo>>(Dispatchers.IO) {
    override suspend fun execute(param: ListPhotoParam): List<Photo> =
        photoRepository.getListPhoto(param.page, param.orderBy.value)
}
