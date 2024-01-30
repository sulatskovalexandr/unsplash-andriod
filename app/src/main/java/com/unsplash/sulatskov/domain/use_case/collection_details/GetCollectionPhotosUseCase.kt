package com.unsplash.sulatskov.domain.use_case.collection_details

import com.unsplash.sulatskov.UseCase
import com.unsplash.sulatskov.domain.model.CollectionPhotos
import com.unsplash.sulatskov.domain.repository.CollectionDetailsRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

 data class ParamCollectionPhotos(
    val id:String,
    val page:Int
)

/**
 * Получение списка фотографий коллекций
 */
class GetCollectionPhotosUseCase @Inject constructor(private val collectionDetailsRepository: CollectionDetailsRepository) :
    UseCase<ParamCollectionPhotos, List<CollectionPhotos>>(Dispatchers.IO) {
    override suspend fun execute(param: ParamCollectionPhotos): List<CollectionPhotos> =
       collectionDetailsRepository.getListCollectionPhotos(param.id, param.page)
}