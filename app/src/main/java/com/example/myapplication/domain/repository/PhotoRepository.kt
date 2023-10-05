package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Photo

interface PhotoRepository {
    suspend fun getListPhoto(page: Int, oderBy: String): List<Photo>
    suspend fun getDataBaseListPhoto(page: Int): List<Photo>
}