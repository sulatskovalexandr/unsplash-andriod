package com.unsplash.sulatskov.domain.repository

import com.unsplash.sulatskov.domain.model.CollectionDetails
import com.unsplash.sulatskov.domain.model.CollectionPhotos

/**
 * Репозиторий для информации о коллекции
 */
interface CollectionDetailsRepository {

    suspend fun getCollectionDetails(collectionId: String): CollectionDetails

    suspend fun getListCollectionPhotos(collectionId:String, page:Int):List<CollectionPhotos>
}