package nl.farid.android.photoalbum.business.data.cache.implementation

import nl.farid.android.photoalbum.business.data.cache.abstraction.IPhotoAlbumCacheDataSource
import nl.farid.android.photoalbum.presentation.datasource.cache.dao.PhotoAlbumDao
import nl.farid.android.photoalbum.presentation.datasource.network.service.PhotoAlbumService
import javax.inject.Inject

class PhotoAlbumCacheDataSource
@Inject constructor(
    private val photoAlbumDao: PhotoAlbumDao
): IPhotoAlbumCacheDataSource{
}