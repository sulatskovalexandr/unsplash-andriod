дpackage com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data_base.PhotoDao
import com.example.myapplication.data_base.PhotoDataBase
import com.example.myapplication.general_screen.domain.model.Photo
import dagger.Module
import dagger.Provides

@Module
class DbModule {

    @Provides
    fun providePhotoDataBase(context: Context): PhotoDataBase = Room.databaseBuilder(
        context,
        PhotoDataBase::class.java,
        Photo.TABLE_NAME
    )
        .build()


    @Provides
    fun providePhotoDao(dataBase: PhotoDataBase): PhotoDao =
        dataBase.photoDao()

}