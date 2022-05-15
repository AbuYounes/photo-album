package nl.farid.android.photoalbum.business.usecase.implementation

import nl.farid.android.photoalbum.business.repository.abstraction.IPhotoAlbumRepository
import nl.farid.android.photoalbum.business.usecase.abstraction.GetPhotosFromAlbumResult
import nl.farid.android.photoalbum.business.usecase.abstraction.IGetPhotosFromAlbum
import java.lang.Exception
import javax.inject.Inject

class GetPhotosFromAlbum
@Inject constructor(
    private val iPhotoAlbumRepository: IPhotoAlbumRepository
): IGetPhotosFromAlbum {

    override suspend fun getPhotoFromAlbum(albumId: Int): GetPhotosFromAlbumResult {
        return try {
            GetPhotosFromAlbumResult.Success(iPhotoAlbumRepository.getPhotosFromAlbum(albumId))
        } catch (e: Exception){
            GetPhotosFromAlbumResult.Error(e)
        }
    }
}