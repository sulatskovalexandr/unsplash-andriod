package com.unsplash.sulatskov.domain.repository

import com.unsplash.sulatskov.domain.model.Collection

interface CollectionRepository {
    suspend fun getListCollections(
        page: Int,
    ): List<Collection>
}