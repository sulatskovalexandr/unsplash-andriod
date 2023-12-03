package com.example.myapplication.data.repository

import com.example.myapplication.constants.Const.GRANT_TYPE
import com.example.myapplication.constants.Const.REDIRECT_URI
import com.example.myapplication.domain.model.AccessToken
import com.example.myapplication.domain.model.Me
import com.example.myapplication.domain.repository.LoginRepository
import com.example.myapplication.services.ApiService.PhotoApiService
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService,
    private val accessTokenProvider: AccessTokenProvider,
) : LoginRepository {

    override suspend fun getAccessToken(code: String): AccessToken =
        photoApiService.userAuthorization(
            accessTokenProvider.clientId.toString(),
            accessTokenProvider.clientSecret.toString(),
            REDIRECT_URI,
            code,
            GRANT_TYPE
        )

    override suspend fun getMe(): Me =
        photoApiService.getMe()

    fun isAuthorized() = accessTokenProvider.isAuthorized

    fun getUserName() = accessTokenProvider.userName

    fun getEmail() = accessTokenProvider.email

    fun getProfileImage() = accessTokenProvider.profileImage

    fun logout() = accessTokenProvider.reset()
}