package com.unsplash.sulatskov.data.repository

import com.unsplash.sulatskov.domain.model.Collection
import com.unsplash.sulatskov.domain.repository.CollectionRepository
import com.unsplash.sulatskov.services.ApiService.PhotoApiService
import com.unsplash.sulatskov.ui.base.BaseFragment
import javax.inject.Inject

/**
 * Имплементация [CollectionRepository]
 */
class CollectionRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService
) : CollectionRepository {

    /**
     * Получает список коллекций с сервера
     * @param page - страница из 10 элементов
     */
    override suspend fun getListCollections(page: Int): List<Collection> =
        photoApiService.getCollections(page, 10)
}