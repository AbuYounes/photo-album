package nl.farid.android.photoalbum.presentation.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import nl.farid.android.photoalbum.model.entity.AlbumEntity
import nl.farid.android.photoalbum.presentation.datasource.cache.dao.PhotoAlbumDao

@Database(entities = [AlbumEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getPhotoAlbumDao(): PhotoAlbumDao

    companion object{
        const val DATABASE_NAME = "photoAlbum_db"
    }
}