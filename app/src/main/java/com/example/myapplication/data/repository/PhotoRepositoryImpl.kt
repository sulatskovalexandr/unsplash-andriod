package com.example.myapplication.data.repository

import com.example.myapplication.data_base.PhotoDao
import com.example.myapplication.domain.model.Photo

import com.example.myapplication.domain.repository.PhotoRepository
import com.example.myapplication.services.ApiService.PhotoApiService
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService,
    private val photoDao: PhotoDao,
) : PhotoRepository {

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

    override suspend fun getDataBaseListPhoto(page: Int, oderBy: String): List<Photo> =
        photoDao.getPhotos(10)
}