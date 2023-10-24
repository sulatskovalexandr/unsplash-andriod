package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.AccessToken
import com.example.myapplication.domain.model.Me

interface LoginRepository {

    suspend fun getAccessToken(code: String): AccessToken

    suspend fun getMe():Me
}