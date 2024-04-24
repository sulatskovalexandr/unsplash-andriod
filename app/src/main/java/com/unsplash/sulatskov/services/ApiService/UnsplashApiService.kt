package com.unsplash.sulatskov.services.ApiService

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.unsplash.sulatskov.domain.model.*
import com.unsplash.sulatskov.domain.model.dto.PhotoDto
import com.unsplash.sulatskov.data.paging.CollectionPagingSource
import javax.inject.Inject


class UnsplashApiService @Inject constructor(private val unsplashApi: UnsplashApi) {

    /**
     * photo
     */
    suspend fun getPhotos(page: Int, perPage: Int, orderBy: String): List<PhotoDto> =
        unsplashApi.getPhotos(page, perPage, orderBy)

    /**
     * photo_details
     */
    suspend fun getPhotoDetails(photoId: String): PhotoDetails? =
        unsplashApi.getPhotoDetails(photoId)

    suspend fun getPhotoStatistics(photoId: String): PhotoStatistics? =
        unsplashApi.getPhotoStatistics(photoId)

    suspend fun getDownloadPhotoUrl(photoId: String): DownloadPhotoUrl? =
        unsplashApi.getDownloadPhotoUri(photoId)

    /**
     * collection
     */
    suspend fun getCollections(
        page: Int,
        perPage: Int
    ): List<CollectionDto> =
        unsplashApi.getCollections(page, perPage)

    fun getPagingCollection() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
            ),
            pagingSourceFactory = {
                CollectionPagingSource(unsplashApi)
            }
        ).flow

    /**
     * collection_details
     */
    suspend fun getCollectionDetails(collectionId: String): CollectionDetails =
        unsplashApi.getCollectionsDetails(collectionId)

    suspend fun getCollectionPhotos(
        collectionId: String,
        page: Int,
        perPage: Int
    ): List<CollectionPhotos> =
        unsplashApi.getCollectionPhotos(collectionId, page, perPage)

    /**
     * user
     */
    suspend fun getUser(userName: String): User =
        unsplashApi.getUser(userName)

    suspend fun getUsersPhoto(userName: String, page: Int): List<UserPhoto> =
        unsplashApi.getUsersPhoto(userName, page)

    suspend fun getUserCollection(userName: String, page: Int): List<CollectionDto> =
        unsplashApi.getUserCollections(userName, page)

    suspend fun getMe(): Me =
        unsplashApi.getMe()

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
        unsplashApi.userAuthorization(clientId, clientSecret, redirectUri, code, grantType)

    /**
     *search
     */

    suspend fun getSearchPhotos(
        query: String,
        page: Int,
        perPage: Int,
        orderBy: String,
        contentFilter: String,
        color: String?,
        orientation: String?
    ): SearchPhotos =
        unsplashApi.getSearchPhotos(
            query,
            page,
            perPage,
            orderBy,
            contentFilter,
            color,
            orientation
        )

    suspend fun getSearchCollections(
        query: String,
        page: Int,
        perPage: Int
    ): SearchCollections =
        unsplashApi.getSearchCollections(
            query,
            page,
            perPage
        )

    suspend fun getSearchUsers(
        query: String,
        page: Int,
        perPage: Int
    ): SearchUsers =
        unsplashApi.getSearchUsers(
            query,
            page,
            perPage
        )
}