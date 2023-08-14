package com.example.myapplication.services

import com.example.myapplication.constants.Const.PER_PAGE
import com.example.myapplication.constants.Const.YOUR_ACCESS_KEY
import com.example.myapplication.general_screen.domain.model.PhotoDetails
import com.example.myapplication.main.presentation.general_screen.Photos
import com.example.myapplication.photo_details_screen.domain.model.PhotoStatistics
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
    ): List<Photos>

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
}