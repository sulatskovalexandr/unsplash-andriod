package com.example.myapplication.domain.model

data class PhotoStatistics(
    val downloads: Downloads,
    val views: Views,
    val likes: Likes
)

class Downloads(
    val total: Int = 0
)

class Views(
    val total: Int = 0
)

class Likes(
    val total: Int = 0
)



