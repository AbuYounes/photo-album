package nl.farid.android.photoalbum.business.repository.implementation

import nl.farid.android.photoalbum.business.data.cache.abstraction.IPhotoAlbumCacheDataSource
import nl.farid.android.photoalbum.business.repository.abstraction.IPhotoAlbumRepository
import javax.inject.Inject

class PhotoAlbumRepository
@Inject constructor(
    private val iPhotoAlbumCacheDataSource: IPhotoAlbumCacheDataSource
): IPhotoAlbumRepository{
}