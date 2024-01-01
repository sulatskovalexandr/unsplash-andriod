package com.unsplash.sulatskov.domain.use_case.collection_usecase

import com.unsplash.sulatskov.UseCase
import com.unsplash.sulatskov.domain.model.Collection
import com.unsplash.sulatskov.domain.repository.CollectionRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetCollectionUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) : UseCase<Int, List<Collection>>(Dispatchers.IO) {
    override suspend fun execute(param: Int): List<Collection> =
        collectionRepository.getListCollections(param)


}