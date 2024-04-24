package com.unsplash.sulatskov.domain.repository

import com.unsplash.sulatskov.domain.model.CollectionDto
import com.unsplash.sulatskov.domain.model.User
import com.unsplash.sulatskov.domain.model.dto.PhotoDto

interface SearchRepository {

    suspend fun getSearchPhotos(
        query: String,
        page: Int,
        perPage:Int,
        orderBy: String,
        contentFilter: String,
        color: String?,
        orientation: String?
    ): List<PhotoDto>

    suspend fun getSearchCollections(
        query: String,
        page: Int,
        perPage:Int,
    ): List<CollectionDto>

    suspend fun getSearchUsers(
        query: String,
        page: Int,
        perPage:Int,
    ): List<User>
}