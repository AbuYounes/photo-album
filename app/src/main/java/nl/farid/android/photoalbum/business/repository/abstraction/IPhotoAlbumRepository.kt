package nl.farid.android.photoalbum.business.repository.abstraction

import kotlinx.coroutines.flow.Flow
import nl.farid.android.photoalbum.model.app_model.Album
import nl.farid.android.photoalbum.model.app_model.Photo

interface IPhotoAlbumRepository {
    suspend fun markAsFavorite(album: Album)
    suspend fun getAllFavoriteAlbums(): Flow<List<Album>>
    suspend fun getAllFavoriteAlbumsNoFlow(): List<Album>
    suspend fun deleteAlbum(id: Int)
    suspend fun getAlbums(): List<Album>
    suspend fun getPhotosFromAlbum(albumId: Int): List<Photo>
}