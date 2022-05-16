package nl.farid.android.photoalbum.business.data.cache.abstraction

import kotlinx.coroutines.flow.Flow
import nl.farid.android.photoalbum.model.entity.AlbumEntity

interface IPhotoAlbumCacheDataSource {
    suspend fun insertFavoriteAlbum(albumEntity: AlbumEntity)
    fun getAllFavoriteAlbumsFlow(): Flow<List<AlbumEntity>>
    suspend fun deleteAlbum(id: Int)
}