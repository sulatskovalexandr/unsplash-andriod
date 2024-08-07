package com.unsplash.sulatskov.data.repository

import com.unsplash.sulatskov.data_base.PhotoDao
import com.unsplash.sulatskov.domain.model.Photo

import com.unsplash.sulatskov.domain.repository.PhotoRepository
import com.unsplash.sulatskov.services.ApiService.UnsplashApiService
import javax.inject.Inject

/**
 * Имплементация [PhotoRepository]
 *
 * @param unsplashApiService [UnsplashApiService]
 * @param photoDao [PhotoDao]
 */
class PhotoRepositoryImpl @Inject constructor(
    private val unsplashApiService: UnsplashApiService,
    private val photoDao: PhotoDao,
) : PhotoRepository {

    /**
     * Получает список фото с сервера и добавляет в БД
     *
     * @param page
     * @param oderBy  сортировка списка по параметру enum class Order
     */
    override suspend fun getListPhoto(page: Int, oderBy: String): List<Photo> {

        val photos = unsplashApiService.getPhotos(page = page, 10, oderBy).map { photo ->
            Photo(
                id = photo.id,
                name = photo.user.name.orEmpty(),
                userName = photo.user.userName.orEmpty(),
                profileImage = photo.user.profileImage?.medium.toString(),
                urls = photo.urls?.regular.toString(),
                createdTime = 1L
            )
        }
        photoDao.insertPhotos(photos)
        return photos
    }

    /**
     * Получает список фото с БД
     */
    override suspend fun getDataBaseListPhoto(page: Int, oderBy: String): List<Photo> =
        photoDao.getPhotos(10)
}