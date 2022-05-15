package nl.farid.android.photoalbum.business.usecase.implementation

import nl.farid.android.photoalbum.business.repository.abstraction.IPhotoAlbumRepository
import nl.farid.android.photoalbum.business.usecase.abstraction.IMarkAlbumAsFavorite
import nl.farid.android.photoalbum.business.usecase.abstraction.MarkAlbumAsFavoriteResult
import nl.farid.android.photoalbum.model.app_model.Album
import java.lang.Exception
import javax.inject.Inject

class MarkAlbumAsFavorite
@Inject constructor(
    private val iPhotoAlbumRepository: IPhotoAlbumRepository
): IMarkAlbumAsFavorite {

    override suspend fun markAsFavorite(album: Album): MarkAlbumAsFavoriteResult {
        return try {
            iPhotoAlbumRepository.markAsFavorite(album)
            MarkAlbumAsFavoriteResult.Success
        } catch (e: Exception){
            MarkAlbumAsFavoriteResult.Error(e)
        }
    }
}