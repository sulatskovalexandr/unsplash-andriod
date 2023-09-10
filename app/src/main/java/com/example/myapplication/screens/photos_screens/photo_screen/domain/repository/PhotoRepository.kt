package com.example.myapplication.screens.photos_screens.photo_screen.domain.repository

import com.example.myapplication.screens.photos_screens.photo_screen.domain.model.Photo

interface PhotoRepository {
    suspend fun getListPhoto(page: Int): List<Photo>
    suspend fun getDataBaseListPhoto(page: Int): List<Photo>
}