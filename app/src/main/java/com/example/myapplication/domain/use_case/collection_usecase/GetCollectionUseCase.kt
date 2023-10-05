package com.example.myapplication.domain.use_case.collection_usecase

import com.example.myapplication.UseCase
import com.example.myapplication.domain.model.Collection
import com.example.myapplication.domain.repository.CollectionRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetCollectionUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) : UseCase<Int, List<Collection>>(Dispatchers.IO) {
    override suspend fun execute(param: Int): List<Collection> =
        collectionRepository.getListCollections(param)


}