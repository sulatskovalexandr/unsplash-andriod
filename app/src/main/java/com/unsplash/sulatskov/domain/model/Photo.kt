package com.unsplash.sulatskov.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Photo.TABLE_NAME)
data class Photo(
    @PrimaryKey val id: String,
    val name: String,
    val userName: String,
    val profileImage: String,
    val urls: String,
    val createdTime: Long
) {
    companion object {
        const val TABLE_NAME = "photos"
    }
}