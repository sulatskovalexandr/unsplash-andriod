package com.unsplash.sulatskov.domain.repository

import com.unsplash.sulatskov.domain.model.AccessToken
import com.unsplash.sulatskov.domain.model.Me

interface LoginRepository {

    suspend fun getAccessToken(code: String): AccessToken

    suspend fun getMe():Me
}