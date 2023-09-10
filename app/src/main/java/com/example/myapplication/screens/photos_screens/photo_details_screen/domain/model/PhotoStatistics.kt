package com.example.myapplication.screens.photos_screens.photo_details_screen.domain.model

data class PhotoStatistics(
    val downloads: Downloads,
    val views: Views,
    val likes: Likes
)

class Downloads(
    val total: Int
)

class Views(
    val total: Int
)

class Likes(
    val total: Int
)



