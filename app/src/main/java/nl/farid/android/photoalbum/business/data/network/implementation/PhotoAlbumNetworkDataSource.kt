package nl.farid.android.photoalbum.business.data.network.implementation

import nl.farid.android.photoalbum.business.data.network.abstraction.IPhotoAlbumNetworkDataSource
import nl.farid.android.photoalbum.model.dto.AlbumDTO
import nl.farid.android.photoalbum.model.dto.PhotoDTO
import nl.farid.android.photoalbum.presentation.datasource.network.service.PhotoAlbumService
import javax.inject.Inject

class PhotoAlbumNetworkDataSource
@Inject constructor(
    private val photoAlbumService: PhotoAlbumService
): IPhotoAlbumNetworkDataSource{

    override suspend fun getAlbums(): List<AlbumDTO> =
        photoAlbumService.getAlbums()


    override suspend fun getPhotosFromAlbum(albumId: Int): List<PhotoDTO> =
        photoAlbumService.getPhotosFromAlbum(albumId)

}