package com.example.myapplication.screens.photos_screens.photo_details_screen.domain.repository

import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.model.DownloadPhotoUrl
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.model.PhotoDetails
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.model.PhotoStatistics
import com.example.myapplication.screens.photos_screens.photo_screen.domain.model.Photo

interface PhotoDetailsRepository {

    suspend fun getPhotoDetail(photoId: String): PhotoDetails?

    suspend fun getPhotoStatistics(photoId: String): PhotoStatistics?

    suspend fun getDownloadPhotoUrl(photoId: String): DownloadPhotoUrl

    suspend fun downloadPhoto(fileName: String, url: String): Long
    suspend fun getDataBasePhoto(userId: String): Photo
//    suspend fun isPhotoExist(fileName: String): Boolean
}