package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Collection

interface CollectionRepository {
    suspend fun getListCollections(
        page: Int,
    ): List<Collection>
}