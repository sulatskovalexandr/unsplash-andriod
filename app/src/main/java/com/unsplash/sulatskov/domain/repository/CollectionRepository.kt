package com.unsplash.sulatskov.domain.repository

import com.unsplash.sulatskov.domain.model.Collection

/**
 * Репозиторий для коллекций
 */
interface CollectionRepository {
    suspend fun getListCollections(
        page: Int,
    ): List<Collection>
}