package com.unsplash.sulatskov.data.repository

import com.unsplash.sulatskov.domain.model.CollectionDto
import com.unsplash.sulatskov.domain.model.User
import com.unsplash.sulatskov.domain.model.UserPhoto
import com.unsplash.sulatskov.domain.repository.UserRepository
import com.unsplash.sulatskov.services.ApiService.UnsplashApiService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val unsplashApiService: UnsplashApiService,
) : UserRepository {

    /**
     * Получает список фото пользователя с сервера
     */
    override suspend fun getUserPhoto(userName: String, page: Int): List<UserPhoto> =
        unsplashApiService.getUsersPhoto(userName, page)

    /**
     * Получает список коллекций пользователя с сервера
     */
    override suspend fun getUserCollection(userName: String, page: Int): List<CollectionDto> =
        unsplashApiService.getUserCollection(userName, page)

    /**
     * Получает информацию пользователя с сервера
     */
    override suspend fun getUser(userName: String): User =
        unsplashApiService.getUser(userName)
}