package com.unsplash.sulatskov.domain.repository

import com.unsplash.sulatskov.domain.model.Photo

interface PhotoRepository {
    suspend fun getListPhoto(page: Int, oderBy: String): List<Photo>

    suspend fun getDataBaseListPhoto(page: Int, oderBy: String): List<Photo>
}