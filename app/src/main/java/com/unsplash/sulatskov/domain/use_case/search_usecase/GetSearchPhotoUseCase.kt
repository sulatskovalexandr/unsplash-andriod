package com.unsplash.sulatskov.domain.use_case.search_usecase

import com.unsplash.sulatskov.UseCase
import com.unsplash.sulatskov.domain.model.dto.PhotoDto
import com.unsplash.sulatskov.domain.repository.SearchRepository
import com.unsplash.sulatskov.ui.search_screen.search_photo.ColorSearchPhoto
import com.unsplash.sulatskov.ui.search_screen.search_photo.ContentFilterSearchPhoto
import com.unsplash.sulatskov.ui.search_screen.search_photo.OrderSearchPhoto
import com.unsplash.sulatskov.ui.search_screen.search_photo.OrientationSearchPhoto

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

data class SearchPhotoParam(
    val query: String,
    val page: Int,
    val orderBy: OrderSearchPhoto,
    val contentFilter: ContentFilterSearchPhoto,
    val color: ColorSearchPhoto?,
    val orientation: OrientationSearchPhoto?
)

class GetSearchPhotoUseCase @Inject constructor(private val searchRepository: SearchRepository) :
    UseCase<SearchPhotoParam, List<PhotoDto>>(Dispatchers.IO) {
    override suspend fun execute(param: SearchPhotoParam): List<PhotoDto> =
        searchRepository.getSearchPhotos(
            query = param.query,
            page = param.page,
            10,
            orderBy = param.orderBy.value,
            contentFilter = param.contentFilter.value,
            color = param.color?.value,
            orientation = param.orientation?.value
        )
}
