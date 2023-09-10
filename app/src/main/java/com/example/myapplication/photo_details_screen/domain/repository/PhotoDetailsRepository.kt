package com.example.myapplication.photo_details_screen.domain.repository

import com.example.myapplication.general_screen.domain.dto.DownloadPhotoUrl
import com.example.myapplication.general_screen.domain.dto.PhotoDetails
import com.example.myapplication.photo_details_screen.domain.model.PhotoStatistics

interface PhotoDetailsRepository {

    suspend fun getPhotoDetail(photoId: String): PhotoDetails?

    suspend fun getPhotoStatistics(photoId: String): PhotoStatistics?

    suspend fun getDownloadPhotoUrl(photoId: String): DownloadPhotoUrl

    suspend fun downloadPhoto(fileName: String, url: String): Long
}