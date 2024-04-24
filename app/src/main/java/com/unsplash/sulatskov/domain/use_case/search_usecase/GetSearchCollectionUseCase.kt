package com.unsplash.sulatskov.domain.use_case.search_usecase

import com.unsplash.sulatskov.UseCase
import com.unsplash.sulatskov.domain.model.CollectionDto
import com.unsplash.sulatskov.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

data class SearchCollectionParam(
    val query: String,
    val page: Int,
)

class GetSearchCollectionUseCase @Inject constructor(private val searchRepository: SearchRepository) :
    UseCase<SearchCollectionParam, List<CollectionDto>>(Dispatchers.IO) {
    override suspend fun execute(param: SearchCollectionParam): List<CollectionDto> =
        searchRepository.getSearchCollections(param.query, param.page, 10)
}