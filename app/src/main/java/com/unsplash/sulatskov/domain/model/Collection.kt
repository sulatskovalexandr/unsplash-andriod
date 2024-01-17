package com.unsplash.sulatskov.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Collection.TABLE_NAME)
data class Collection(
    @PrimaryKey val id: String,
    val userName: String,
    val profileImage: String,
    val title: String,
    val urls: String,
    val totalPhoto: Int = 0,
    val createdTime: Long
) {
    companion object {
        const val TABLE_NAME = "collection"
    }
}