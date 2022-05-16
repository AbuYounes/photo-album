package nl.farid.android.photoalbum.business.data.cache.implementation

import kotlinx.coroutines.flow.Flow
import nl.farid.android.photoalbum.business.data.cache.abstraction.IPhotoAlbumCacheDataSource
import nl.farid.android.photoalbum.model.entity.AlbumEntity
import nl.farid.android.photoalbum.presentation.datasource.cache.dao.PhotoAlbumDao
import javax.inject.Inject

class PhotoAlbumCacheDataSource
@Inject constructor(
    private val photoAlbumDao: PhotoAlbumDao
): IPhotoAlbumCacheDataSource{

    override suspend fun insertFavoriteAlbum(albumEntity: AlbumEntity) =
        photoAlbumDao.insertPhotoAlbum(albumEntity)

    override fun getAllFavoriteAlbumsFlow(): Flow<List<AlbumEntity>> =
        photoAlbumDao.getAllAlbumsFlow()

    override suspend fun deleteAlbum(id: Int) {
        photoAlbumDao.deletePhotoAlbum(id)
    }
}