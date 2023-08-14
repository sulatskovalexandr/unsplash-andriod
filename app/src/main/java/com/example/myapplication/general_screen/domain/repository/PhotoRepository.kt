package com.example.myapplication.general_screen.domain.repository

import com.example.myapplication.main.presentation.general_screen.Photos

interface PhotoRepository {

    suspend fun getListPhoto(page: Int): List<Photos>
}