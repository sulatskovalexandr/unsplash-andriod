package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.DownloadPhotoUrl
import com.example.myapplication.domain.model.Photo
import com.example.myapplication.domain.model.PhotoDetails
import com.example.myapplication.domain.model.PhotoStatistics

interface PhotoDetailsRepository {

    suspend fun getPhotoDetail(photoId: String): PhotoDetails?

    suspend fun getPhotoStatistics(photoId: String): PhotoStatistics?

    suspend fun getDownloadPhotoUrl(photoId: String): DownloadPhotoUrl

    suspend fun downloadPhoto(fileName: String, url: String): Long
    suspend fun getDataBasePhoto(userId: String): Photo
}