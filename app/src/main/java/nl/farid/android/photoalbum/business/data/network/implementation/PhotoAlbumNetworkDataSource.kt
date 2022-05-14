package nl.farid.android.photoalbum.business.data.network.implementation

import nl.farid.android.photoalbum.business.data.network.abstraction.IPhotoAlbumNetworkDataSource
import nl.farid.android.photoalbum.presentation.datasource.network.service.PhotoAlbumService
import javax.inject.Inject

class PhotoAlbumNetworkDataSource
@Inject constructor(
    private val photoAlbumService: PhotoAlbumService
): IPhotoAlbumNetworkDataSource{
}