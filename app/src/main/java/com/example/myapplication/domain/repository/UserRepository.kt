package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.model.UserPhoto

interface
UserRepository {

    suspend fun getUser(userName: String): User

    suspend fun getUserPhoto(userName: String, page: Int): List<UserPhoto>

    suspend fun getUserCollection(
        userName: String,
        page: Int
    ): List<com.example.myapplication.domain.model.Collection>


}