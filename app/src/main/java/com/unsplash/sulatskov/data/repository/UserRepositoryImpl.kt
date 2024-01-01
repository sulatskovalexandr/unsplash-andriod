package com.unsplash.sulatskov.data.repository

import com.unsplash.sulatskov.domain.model.Collection
import com.unsplash.sulatskov.domain.model.User
import com.unsplash.sulatskov.domain.model.UserPhoto
import com.unsplash.sulatskov.domain.repository.UserRepository
import com.unsplash.sulatskov.services.ApiService.PhotoApiService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService,
) : UserRepository {
    override suspend fun getUserPhoto(userName: String, page: Int): List<UserPhoto> =
        photoApiService.getUsersPhoto(userName, page)

    override suspend fun getUserCollection(userName: String, page: Int): List<Collection> =
        photoApiService.getUserCollection(userName, page)


    override suspend fun getUser(userName: String): User =
        photoApiService.getUser(userName)

}
