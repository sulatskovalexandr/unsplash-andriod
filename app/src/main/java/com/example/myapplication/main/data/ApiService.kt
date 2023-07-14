package com.example.myapplication.main.data

import com.example.myapplication.constants.Const.BASE_URL
import com.example.myapplication.main.screens.general_screen.Photos
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiPhotoService {
    private val retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(UnsplashPhotoApi::class.java)

    suspend fun getPhotos(page: Int, perPage: Int): List<Photos> = service.getPhotos(page, perPage)


}