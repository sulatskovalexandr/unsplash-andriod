package com.example.myapplication.data.repository

import com.example.myapplication.domain.model.Collection
import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.model.UserPhoto
import com.example.myapplication.domain.repository.UserRepository
import com.example.myapplication.services.ApiService.PhotoApiService
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
