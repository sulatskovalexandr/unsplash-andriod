package com.unsplash.sulatskov.data.repository

import com.unsplash.sulatskov.domain.model.DownloadPhotoUrl
import com.unsplash.sulatskov.domain.model.Photo
import com.unsplash.sulatskov.domain.model.PhotoDetails
import com.unsplash.sulatskov.domain.model.PhotoStatistics
import com.unsplash.sulatskov.domain.repository.PhotoDetailsRepository
import com.unsplash.sulatskov.services.ApiService.UnsplashApiService
import com.unsplash.sulatskov.services.photoDownloadService.PhotoDownloader
import javax.inject.Inject

/**
 * Имплементация [PhotoDetailsRepository]
 *
 * @param unsplashApiService [UnsplashApiService]
 * @param photoDownloader [PhotoDownloader]
 */
class PhotoDetailsRepositoryImpl @Inject constructor(
    private val unsplashApiService: UnsplashApiService,
    private val photoDownloader: PhotoDownloader
) :
    PhotoDetailsRepository {

    /**
     * Получает информацию о фото с сервера
     *
     * @param photoId   id фото
     */
    override suspend fun getPhotoDetail(photoId: String): PhotoDetails? =
        unsplashApiService.getPhotoDetails(photoId = photoId)

    /**
     * Получает статистику фото с сервера
     *
     * @param photoId   id фото
     */
    override suspend fun getPhotoStatistics(photoId: String): PhotoStatistics? =
        unsplashApiService.getPhotoStatistics(photoId = photoId)

    /**
     * Получает Url фото с сервера
     *
     * @param photoId   id фото
     */
    override suspend fun getDownloadPhotoUrl(photoId: String): DownloadPhotoUrl =
        unsplashApiService.getDownloadPhotoUrl(photoId)!!

    /**
     * Загружает фото на устройство
     *
     * @param fileName
     * @param url
     */
    override suspend fun downloadPhoto(fileName: String, url: String): Long =
        photoDownloader.downloadFile(fileName, url)

//    override suspend fun isPhotoExist(fileName: String): Boolean =
//        photoDownloader.isPhotoExists(fileName)

    /**
     * Получает информацию о фото из БД
     *
     */
    override suspend fun getDataBasePhoto(userId: String): Photo {
        TODO("Not yet implemented")
    }
}