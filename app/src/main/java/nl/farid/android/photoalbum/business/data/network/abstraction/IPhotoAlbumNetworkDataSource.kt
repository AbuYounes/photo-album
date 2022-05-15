package nl.farid.android.photoalbum.business.data.network.abstraction

import nl.farid.android.photoalbum.model.dto.AlbumDTO
import nl.farid.android.photoalbum.model.dto.PhotoDTO

interface IPhotoAlbumNetworkDataSource {
    suspend fun getAlbums(): List<AlbumDTO>
    suspend fun getPhotosFromAlbum(albumId: Int): List<PhotoDTO>
}
