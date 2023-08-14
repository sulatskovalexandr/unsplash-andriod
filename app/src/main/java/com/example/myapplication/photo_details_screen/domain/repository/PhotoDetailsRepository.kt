package com.example.myapplication.photo_details_screen.domain.repository

import com.example.myapplication.general_screen.domain.model.PhotoDetails
import com.example.myapplication.photo_details_screen.domain.model.PhotoStatistics

interface PhotoDetailsRepository {

    suspend fun getPhotoDetail(photoId: String): PhotoDetails?

    suspend fun getPhotoStatistics(photoId: String): PhotoStatistics?

}