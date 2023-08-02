package com.example.myapplication.main.data

import com.example.myapplication.constants.Const.PER_PAGE
import com.example.myapplication.constants.Const.YOUR_ACCESS_KEY
import com.example.myapplication.main.screens.general_screen.Photos
import retrofit2.http.GET
import retrofit2.http.Headers
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

}