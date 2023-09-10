package com.example.myapplication.services.ApiService

import com.example.myapplication.constants.Const.PER_PAGE
import com.example.myapplication.constants.Const.YOUR_ACCESS_KEY
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.model.DownloadPhotoUrl
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.model.PhotoDetails
import com.example.myapplication.screens.photos_screens.photo_details_screen.domain.model.PhotoStatistics
import com.example.myapplication.screens.photos_screens.photo_screen.domain.dto.PhotoDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashPhotoApi {
    @GET("photos")
    @Headers(
        "Accept-Version: v1",
    )
    suspend fun getPhotos(
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int? = PER_PAGE,
        @Query("client_id") clientId: String = YOUR_ACCESS_KEY
    ): List<PhotoDto>

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
}