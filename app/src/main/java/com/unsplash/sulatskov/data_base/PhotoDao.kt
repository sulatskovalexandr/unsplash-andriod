package com.unsplash.sulatskov.data_base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.unsplash.sulatskov.domain.model.Photo

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<Photo>)

    @Query("SELECT * FROM ${Photo.TABLE_NAME} order by createdTime desc limit :count")
    suspend fun getPhotos(count: Int): List<Photo>

    @Query("SELECT * FROM ${Photo.TABLE_NAME} WHERE id =:photoId")
    suspend fun getPhoto(photoId: String): Photo
}
