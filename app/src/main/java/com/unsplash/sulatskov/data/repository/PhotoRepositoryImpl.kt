package com.unsplash.sulatskov.data.repository

import com.unsplash.sulatskov.data_base.PhotoDao
import com.unsplash.sulatskov.domain.model.Photo

import com.unsplash.sulatskov.domain.repository.PhotoRepository
import com.unsplash.sulatskov.services.ApiService.PhotoApiService
import javax.inject.Inject

/**
 * Имплементация [PhotoRepository]
 *
 * @param photoApiService [PhotoApiService]
 * @param photoDao [PhotoDao]
 */
class PhotoRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService,
    private val photoDao: PhotoDao,
) : PhotoRepository {

    /**
     * Получает список фото с сервера и добавляет в БД
     *
     * @param page
     * @param oderBy  сортировка списка по параметру enum class Order
     */
    override suspend fun getListPhoto(page: Int, oderBy: String): List<Photo> {

        val photos = photoApiService.getPhotos(page = page, 10, oderBy).map { photo ->
            Photo(
                photo.id,
                photo.user.userName.orEmpty(),
//                photo.user?.name.orEmpty(),
                photo.user.profileImage?.medium.toString(),
                photo.urls?.regular.toString(),
                1L
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