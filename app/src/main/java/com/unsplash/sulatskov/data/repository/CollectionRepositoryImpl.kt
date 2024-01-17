package com.unsplash.sulatskov.data.repository

import com.unsplash.sulatskov.data_base.CollectionDao
import com.unsplash.sulatskov.domain.model.Collection
import com.unsplash.sulatskov.domain.repository.CollectionRepository
import com.unsplash.sulatskov.services.ApiService.PhotoApiService
import javax.inject.Inject

/**
 * Имплементация [CollectionRepository]
 */
class CollectionRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService,
    private val collectionDao: CollectionDao
) : CollectionRepository {

    /**
     * Получает список коллекций с сервера
     * @param page - страница из 10 элементов
     */
    override suspend fun getListCollections(page: Int): List<Collection> {
        val collections = photoApiService.getCollections(page, 10).map { collection ->
            Collection(
                id = collection.id,
                userName = collection.user.userName.toString(),
                profileImage = collection.user.profileImage.medium.toString(),
                title = collection.title,
                urls = collection.coverPhoto.url?.regular.toString(),
                totalPhoto = collection.totalPhoto,
                createdTime = 1L
            )
        }
        collectionDao.insertCollections(collections)
        return collections
    }

    override suspend fun getDataBaseListCollections(page: Int): List<com.unsplash.sulatskov.domain.model.Collection> =
     collectionDao.getCollections(10)
}