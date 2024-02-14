package com.unsplash.sulatskov.domain.use_case.collection_details

import com.unsplash.sulatskov.UseCase
import com.unsplash.sulatskov.domain.model.CollectionDetails
import com.unsplash.sulatskov.domain.repository.CollectionDetailsRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Получение информаании о коллекции
 */
class GetCollectionDetailsUseCase @Inject constructor(private val collectionDetailsRepository: CollectionDetailsRepository) :
    UseCase<String, CollectionDetails>(Dispatchers.IO) {

    override suspend fun execute(collectionId: String): CollectionDetails =
        collectionDetailsRepository.getCollectionDetails(collectionId)
}