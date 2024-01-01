package com.unsplash.sulatskov.data.repository

import com.unsplash.sulatskov.constants.Const.GRANT_TYPE
import com.unsplash.sulatskov.constants.Const.REDIRECT_URI
import com.unsplash.sulatskov.domain.model.AccessToken
import com.unsplash.sulatskov.domain.model.Me
import com.unsplash.sulatskov.domain.repository.LoginRepository
import com.unsplash.sulatskov.services.ApiService.PhotoApiService
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