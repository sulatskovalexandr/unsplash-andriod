package com.unsplash.sulatskov.domain.repository

import com.unsplash.sulatskov.domain.model.DownloadPhotoUrl
import com.unsplash.sulatskov.domain.model.Photo
import com.unsplash.sulatskov.domain.model.PhotoDetails
import com.unsplash.sulatskov.domain.model.PhotoStatistics

interface PhotoDetailsRepository {

    suspend fun getPhotoDetail(photoId: String): PhotoDetails?

    suspend fun getPhotoStatistics(photoId: String): PhotoStatistics?

    suspend fun getDownloadPhotoUrl(photoId: String): DownloadPhotoUrl

    suspend fun downloadPhoto(fileName: String, url: String): Long
    suspend fun getDataBasePhoto(userId: String): Photo
}