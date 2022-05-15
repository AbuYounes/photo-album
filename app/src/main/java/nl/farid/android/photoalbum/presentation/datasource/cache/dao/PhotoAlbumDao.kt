package nl.farid.android.photoalbum.presentation.datasource.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nl.farid.android.photoalbum.model.app_model.Album
import nl.farid.android.photoalbum.model.entity.AlbumEntity

@Dao
interface PhotoAlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotoAlbum(albumEntity: AlbumEntity)

    @Query("SELECT * FROM album")
    fun getAllAlbums(): Flow<List<AlbumEntity>>

    @Query("DELETE FROM album WHERE id = :id")
    suspend fun deletePhotoAlbum(id: Int)
}