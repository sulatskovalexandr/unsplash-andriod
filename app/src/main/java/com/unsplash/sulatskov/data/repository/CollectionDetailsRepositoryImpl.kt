package com.unsplash.sulatskov.data.repository

import com.unsplash.sulatskov.domain.model.CollectionDetails
import com.unsplash.sulatskov.domain.model.CollectionPhotos
import com.unsplash.sulatskov.domain.repository.CollectionDetailsRepository
import com.unsplash.sulatskov.services.ApiService.UnsplashApiService
import javax.inject.Inject

/**
 * Имплементация [CollectionDetailsRepository]
 */
class CollectionDetailsRepositoryImpl @Inject constructor(private val unsplashApiService: UnsplashApiService) :
    CollectionDetailsRepository {

    override suspend fun getCollectionDetails(collectionId: String): CollectionDetails =
        unsplashApiService.getCollectionDetails(collectionId)

    override suspend fun getListCollectionPhotos(
        collectionId: String,
        page: Int
    ): List<CollectionPhotos> =
        unsplashApiService.getCollectionPhotos(collectionId, page, 10)
}