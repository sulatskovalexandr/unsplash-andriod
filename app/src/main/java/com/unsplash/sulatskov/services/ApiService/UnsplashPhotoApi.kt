package com.unsplash.sulatskov.services.ApiService

import com.unsplash.sulatskov.constants.Const.GRANT_TYPE
import com.unsplash.sulatskov.constants.Const.PER_PAGE
import com.unsplash.sulatskov.constants.Const.REDIRECT_URI
import com.unsplash.sulatskov.constants.Const.YOUR_ACCESS_KEY
import com.unsplash.sulatskov.constants.Const.YOUR_SECRET_KEY
import com.unsplash.sulatskov.domain.model.*
import com.unsplash.sulatskov.domain.model.Collection
import com.unsplash.sulatskov.domain.model.dto.PhotoDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
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
        @Query("per_page") perPage: Int? = PER_PAGE,
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
     *collection
     */

    @GET("collections")
    suspend fun getCollections(
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int? = PER_PAGE,
        @Query("client_id") clientId: String = YOUR_ACCESS_KEY
    ): List<Collection>

    /**
     *user
     */

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username")
        userName: String,
        @Query("client_id") clientId: String = YOUR_ACCESS_KEY
    ): User

    @GET("users/{username}/photos")
    suspend fun getUsersPhoto(
        @Path("username")
        userName: String,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int? = PER_PAGE,
        @Query("client_id") clientId: String = YOUR_ACCESS_KEY
    ): List<UserPhoto>

    @GET("users/{username}/collections")
    suspend fun getUserCollections(
        @Path("username")
        userName: String,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int? = PER_PAGE,
        @Query("client_id") clientId: String = YOUR_ACCESS_KEY
    ): List<Collection>

    @GET("me")
    suspend fun getMe(): Me

    @POST("oauth/token")
    suspend fun userAuthorization(
        @Query("client_id") clientId: String = YOUR_ACCESS_KEY,
        @Query("client_secret") clientSecret:String = YOUR_SECRET_KEY,
        @Query("redirect_uri") redirectUri:String = REDIRECT_URI,
        @Query("code") code: String,
        @Query("grant_type") grantType: String = GRANT_TYPE
    ):AccessToken
}
