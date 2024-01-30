package com.unsplash.sulatskov.data.repository
import com.unsplash.sulatskov.domain.model.CollectionDetails
import com.unsplash.sulatskov.domain.model.CollectionPhotos
import com.unsplash.sulatskov.domain.repository.CollectionDetailsRepository
import com.unsplash.sulatskov.domain.repository.CollectionRepository
import com.unsplash.sulatskov.services.ApiService.PhotoApiService
import javax.inject.Inject

/**
 * Имплементация [CollectionDetailsRepository]
 */
class CollectionDetailsRepositoryImpl @Inject constructor(private val photoApiService: PhotoApiService) :
    CollectionDetailsRepository {

    override suspend fun getCollectionDetails(collectionId: String): CollectionDetails =
        photoApiService.getCollectionDetails(collectionId)

    override suspend fun getListCollectionPhotos(collectionId:String, page: Int): List<CollectionPhotos> =
        photoApiService.getCollectionPhotos(collectionId ,page, 10)
}