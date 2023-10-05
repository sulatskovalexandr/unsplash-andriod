package com.example.myapplication.services.ApiService

import com.example.myapplication.constants.Const.PER_PAGE_PHOTO
import com.example.myapplication.constants.Const.YOUR_ACCESS_KEY
import com.example.myapplication.domain.model.*
import com.example.myapplication.domain.model.Collection
import com.example.myapplication.domain.model.dto.PhotoDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashPhotoApi {

    /**
     *photos
     */
    @GET("photos")
    @Headers(
        "Accept-Version: v1",
    )

    suspend fun getPhotos(
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int? = PER_PAGE_PHOTO,
        @Query("order_by") orderBy: String,
        @Query("client_id") clientId: String = YOUR_ACCESS_KEY
    ): List<PhotoDto>

    /**
     *photo_details
     */

    @GET("photos/{id}")
    suspend fun getPhotoDetails(
        @Path("id")
        photoId: String,
        @Query("client_id") clientId: String = YOUR_ACCESS_KEY
    ): PhotoDetails?

    @GET("photos/{id}/statistics")
    suspend fun getPhotoStatistics(
        @Path("id")
        photoId: String,
        @Query("client_id") clientId: String = YOUR_ACCESS_KEY
    ): PhotoStatistics?

    @GET("photos/{id}/download")
    suspend fun getDownloadPhotoUri(
        @Path("id")
        photoId: String,
        @Query("client_id") clientId: String = YOUR_ACCESS_KEY
    ): DownloadPhotoUrl?

    /**
     *user
     */
    @GET("users/{username}/photos")
    suspend fun getUsersPhoto(
        @Path("username")
        userName: String,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int? = PER_PAGE_PHOTO,
        @Query("client_id") clientId: String = YOUR_ACCESS_KEY
    ): List<UserPhoto>

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username")
        userName: String,
        @Query("client_id") clientId: String = YOUR_ACCESS_KEY
    ): User

    /**
     *collection
     */

    @GET("collections")
    suspend fun getCollections(
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int? = PER_PAGE_PHOTO,
        @Query("client_id") clientId: String = YOUR_ACCESS_KEY
    ): List<Collection>

    @GET("users/{username}/collections")
    suspend fun getUserCollections(
        @Path("username")
        userName: String,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int? = PER_PAGE_PHOTO,
        @Query("client_id") clientId: String = YOUR_ACCESS_KEY
    ): List<Collection>


}

