package com.unsplash.sulatskov.data_base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.unsplash.sulatskov.domain.model.Photo

/**
 * БД для хранения списка фото
 */
@Database(entities = [Photo::class], version = 2)
abstract class PhotoDataBase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao

    companion object {
        operator fun invoke(context: Context)=
            Room.databaseBuilder(
                context,
                PhotoDataBase::class.java,
                Photo.TABLE_NAME
            ).fallbackToDestructiveMigration()
                .addMigrations(Migration(1, 2){ db ->
                    db.execSQL("alter table ${Photo.TABLE_NAME} add column `name` text not null default''")
                })
                .build()
    }
}
