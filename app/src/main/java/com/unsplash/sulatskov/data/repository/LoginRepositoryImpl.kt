package com.unsplash.sulatskov.data.repository

import com.unsplash.sulatskov.constants.Const.GRANT_TYPE
import com.unsplash.sulatskov.constants.Const.REDIRECT_URI
import com.unsplash.sulatskov.domain.model.AccessToken
import com.unsplash.sulatskov.domain.model.Me
import com.unsplash.sulatskov.domain.repository.LoginRepository
import com.unsplash.sulatskov.services.ApiService.PhotoApiService
import javax.inject.Inject

/**
 * Имплементация [LoginRepository]
 */
class LoginRepositoryImpl @Inject constructor(
    private val photoApiService: PhotoApiService,
    private val accessTokenProvider: AccessTokenProvider,
) : LoginRepository {

    /**
     * Получает AccessToken при авторизпции
     *
     * @param code
     */
    override suspend fun getAccessToken(code: String): AccessToken =
        photoApiService.userAuthorization(
            accessTokenProvider.clientId.toString(),
            accessTokenProvider.clientSecret.toString(),
            REDIRECT_URI,
            code,
            GRANT_TYPE
        )

    /**
     * Получает данные пользователя (мой профиль)
     */
    override suspend fun getMe(): Me =
        photoApiService.getMe()

    /**
     * Проверяет наличие AccessToken
     */
    fun isAuthorized() = accessTokenProvider.isAuthorized

    /**
     * Получает имя пользователя
     */
    fun getUserName() = accessTokenProvider.userName

    /**
     * Получает почту пользователя
     */
    fun getEmail() = accessTokenProvider.email

    /**
     * Получает фото профиля пользователя
     */
    fun getProfileImage() = accessTokenProvider.profileImage

    fun logout() = accessTokenProvider.reset()
}