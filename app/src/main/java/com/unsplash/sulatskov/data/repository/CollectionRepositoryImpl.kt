package com.unsplash.sulatskov.data.repository

import com.unsplash.sulatskov.domain.model.Collection
import com.unsplash.sulatskov.domain.repository.CollectionRepository
import com.unsplash.sulatskov.services.ApiService.PhotoApiService
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService
) : CollectionRepository {

    override suspend fun getListCollections(page: Int): List<Collection> =
        photoApiService.getCollections(page, 10)

}