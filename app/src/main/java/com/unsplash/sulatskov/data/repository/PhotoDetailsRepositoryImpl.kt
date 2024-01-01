package com.unsplash.sulatskov.data.repository

import com.unsplash.sulatskov.domain.model.DownloadPhotoUrl
import com.unsplash.sulatskov.domain.model.Photo
import com.unsplash.sulatskov.domain.model.PhotoDetails
import com.unsplash.sulatskov.domain.model.PhotoStatistics
import com.unsplash.sulatskov.domain.repository.PhotoDetailsRepository
import com.unsplash.sulatskov.services.ApiService.PhotoApiService
import com.unsplash.sulatskov.services.photoDownloadService.PhotoDownloader
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