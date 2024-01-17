package com.unsplash.sulatskov.services.ApiService

import com.unsplash.sulatskov.domain.model.*
import com.unsplash.sulatskov.domain.model.Collection
import com.unsplash.sulatskov.domain.model.dto.PhotoDto
import javax.inject.Inject


class PhotoApiService @Inject constructor(private val unsplashPhotoApi: UnsplashPhotoApi) {

    /**
     * photo
     */

    suspend fun getPhotos(page: Int, perPage: Int, orderBy: String): List<PhotoDto> =
        unsplashPhotoApi.getPhotos(page, perPage, orderBy)

    /**
     * photo_details
     */

    suspend fun getPhotoDetails(photoId: String): PhotoDetails? =
        unsplashPhotoApi.getPhotoDetails(photoId)

    suspend fun getPhotoStatistics(photoId: String): PhotoStatistics? =
        unsplashPhotoApi.getPhotoStatistics(photoId)

    suspend fun getDownloadPhotoUrl(photoId: String): DownloadPhotoUrl? =
        unsplashPhotoApi.getDownloadPhotoUri(photoId)

    /**
     * collection
     */
    suspend fun getCollections(
        page: Int,
        perPage: Int
    ): List<Collection> =
        unsplashPhotoApi.getCollections(page, perPage)

    /**
     * user
     */
    suspend fun getUser(userName: String): User =
        unsplashPhotoApi.getUser(userName)

    suspend fun getUsersPhoto(userName: String, page: Int): List<UserPhoto> =
        unsplashPhotoApi.getUsersPhoto(userName, page)

    suspend fun getUserCollection(userName: String, page: Int): List<Collection> =
        unsplashPhotoApi.getUserCollections(userName, page)

    suspend fun getMe(): Me =
        unsplashPhotoApi.getMe()

    /**
     * login
     */
    suspend fun userAuthorization(
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        code: String,
        grantType: String
    ): AccessToken =
        unsplashPhotoApi.userAuthorization(clientId, clientSecret, redirectUri, code, grantType)
}