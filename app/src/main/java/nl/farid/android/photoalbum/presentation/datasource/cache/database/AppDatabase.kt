package nl.farid.android.photoalbum.presentation.datasource.cache.database

import androidx.room.RoomDatabase
import nl.farid.android.photoalbum.presentation.datasource.cache.dao.PhotoAlbumDao

abstract class AppDatabase: RoomDatabase() {

    abstract fun getPhotoAlbumDao(): PhotoAlbumDao

    companion object{
        const val DATABASE_NAME = "enviolo_db"
    }
}