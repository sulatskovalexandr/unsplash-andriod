package com.example.myapplication.screens.photos_screens.photo_details_screen.data.repository

import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.model.DownloadPhotoUrl
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.model.PhotoDetails
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.model.PhotoStatistics
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.repository.PhotoDetailsRepository
import com.example.myapplication.screens.photos_screens.photo_screen.domain.model.Photo
import com.example.myapplication.services.ApiService.PhotoApiService
import com.example.myapplication.services.photoDownloadService.PhotoDownloader
import javax.inject.Inject

class PhotoDetailsRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService,
    private val photoDownloader: PhotoDownloader
) :
    PhotoDetailsRepository {

    override suspend fun getPhotoDetail(photoId: String): PhotoDetails? =
        photoApiService.getPhotoDetails(photoId = photoId)

    override suspend fun getPhotoStatistics(photoId: String): PhotoStatistics? =
        photoApiService.getPhotoStatistics(photoId = photoId)

    override suspend fun getDownloadPhotoUrl(photoId: String): DownloadPhotoUrl =
        photoApiService.getDownloadPhotoUrl(photoId)!!

    override suspend fun downloadPhoto(fileName: String, url: String): Long =
        photoDownloader.downloadFile(fileName, url)

//    override suspend fun isPhotoExist(fileName: String): Boolean =
//        photoDownloader.isPhotoExists(fileName)

    override suspend fun getDataBasePhoto(userId: String): Photo {
        TODO("Not yet implemented")
    }
}