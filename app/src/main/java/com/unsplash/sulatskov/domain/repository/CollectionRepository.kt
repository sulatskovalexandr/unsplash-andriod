package com.unsplash.sulatskov.domain.repository

import androidx.paging.PagingData
import com.unsplash.sulatskov.domain.model.Collection
import com.unsplash.sulatskov.domain.model.CollectionDto
import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для коллекций
 */
interface CollectionRepository {
    suspend fun getListCollections(page: Int): List<Collection>

    suspend fun getDataBaseListCollections(page: Int): List<Collection>

    fun getPagingCollection(): Flow<PagingData<CollectionDto>>
}