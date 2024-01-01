package com.unsplash.sulatskov.domain.use_case.photo_usecase

import com.unsplash.sulatskov.R
import com.unsplash.sulatskov.UseCase
import com.unsplash.sulatskov.domain.model.Photo
import com.unsplash.sulatskov.domain.repository.PhotoRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

data class ListPhotoParam(
    var page: Int,
    val orderBy: GetPhotoUseCase.Companion.Order
)

/**
 * Получить список фотографий
 */
class GetPhotoUseCase @Inject constructor(private val photoRepository: PhotoRepository) :
    UseCase<ListPhotoParam, List<Photo>>(Dispatchers.IO) {
    override suspend fun execute(param: ListPhotoParam): List<Photo> =
        photoRepository.getListPhoto(param.page, param.orderBy.value)

    companion object {
        enum class Order(val titleRes: Int, val value: String) {
            LATEST(R.string.order_by_latest_text, "latest"),
            OLDEST(R.string.order_by_oldest_text, "oldest"),
            POPULAR(R.string.order_by_popular_text, "popular")
        }
    }
}
