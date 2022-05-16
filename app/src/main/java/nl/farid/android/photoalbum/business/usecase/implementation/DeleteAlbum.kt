package nl.farid.android.photoalbum.business.usecase.implementation

import nl.farid.android.photoalbum.business.repository.abstraction.IPhotoAlbumRepository
import nl.farid.android.photoalbum.business.usecase.abstraction.DeleteAlbumResult
import nl.farid.android.photoalbum.business.usecase.abstraction.IDeleteAlbum
import java.lang.Exception
import javax.inject.Inject

class DeleteAlbum
@Inject constructor(
    private val iPhotoAlbumRepository: IPhotoAlbumRepository
): IDeleteAlbum{

    override suspend fun deleteAlbum(albumId: Int): DeleteAlbumResult {
        return try {
            iPhotoAlbumRepository.deleteAlbum(albumId)
            DeleteAlbumResult.Success
        } catch (e: Exception){
            DeleteAlbumResult.Error(e)
        }
    }
}