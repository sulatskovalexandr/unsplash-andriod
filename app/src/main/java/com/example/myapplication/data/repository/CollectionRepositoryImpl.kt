package com.example.myapplication.data.repository

import com.example.myapplication.domain.model.Collection
import com.example.myapplication.domain.repository.CollectionRepository
import com.example.myapplication.services.ApiService.PhotoApiService
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService
) : CollectionRepository {

    override suspend fun getListCollections(page: Int): List<Collection> =
        photoApiService.getCollections(page, 10)

}