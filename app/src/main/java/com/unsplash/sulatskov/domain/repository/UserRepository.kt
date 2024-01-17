package com.unsplash.sulatskov.domain.repository

import com.unsplash.sulatskov.domain.model.CollectionDto
import com.unsplash.sulatskov.domain.model.User
import com.unsplash.sulatskov.domain.model.UserPhoto

interface
UserRepository {

    suspend fun getUser(userName: String): User

    suspend fun getUserPhoto(userName: String, page: Int): List<UserPhoto>

    suspend fun getUserCollection(
        userName: String,
        page: Int
    ): List<CollectionDto>


}