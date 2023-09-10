package com.example.myapplication.screens.photos_screens.photo_screen.data.repository

import com.example.myapplication.data_base.PhotoDao
import com.example.myapplication.screens.photos_screens.photo_screen.domain.model.Photo

import com.example.myapplication.screens.photos_screens.photo_screen.domain.repository.PhotoRepository
import com.example.myapplication.services.ApiService.PhotoApiService
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService,
    private val photoDao: PhotoDao,
) : PhotoRepository {

    override suspend fun getListPhoto(page: Int): List<Photo> {

        val photos = photoApiService.getPhotos(page = page, 10).map { photo ->
            Photo(
                photo.id,
                photo.user?.userName.orEmpty(),
                photo.user?.profileImage?.medium.toString(),
                photo.urls?.regular.toString(),
                1L
            )
        }
        photoDao.insertPhotos(photos)
        return photos
    }

    override suspend fun getDataBaseListPhoto(page: Int): List<Photo> =
        photoDao.getPhotos(10)
}