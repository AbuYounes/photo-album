package nl.farid.android.photoalbum.business.usecase.implementation

import nl.farid.android.photoalbum.business.repository.abstraction.IPhotoAlbumRepository
import nl.farid.android.photoalbum.business.usecase.abstraction.IGetPhotosFromAlbum
import javax.inject.Inject

class GetPhotosFromAlbum
@Inject constructor(
    private val iPhotoAlbumRepository: IPhotoAlbumRepository
): IGetPhotosFromAlbum {
}