package com.example.myapplication.data_base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.domain.model.Photo

@Database(entities = [Photo::class], version = 1)

abstract class PhotoDataBase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}