package nl.farid.android.photoalbum.presentation.datasource.network.service

import nl.farid.android.photoalbum.model.dto.AlbumDTO
import nl.farid.android.photoalbum.model.dto.PhotoDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoAlbumService {
    @GET("albums")
    suspend fun getPhotoAlbums(): List<AlbumDTO>

    @GET("photos")
    suspend fun getPhotosFromAlbum(
        @Query("albumId") albumId: Int,
    ): List<PhotoDTO>

}