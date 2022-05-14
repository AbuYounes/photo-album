package nl.farid.android.photoalbum.business.usecase.implementation

import nl.farid.android.photoalbum.business.repository.abstraction.IPhotoAlbumRepository
import nl.farid.android.photoalbum.business.usecase.abstraction.IGetAlbums
import javax.inject.Inject

class GetAlbums
@Inject constructor(
    private val iPhotoAlbumRepository: IPhotoAlbumRepository
): IGetAlbums{
}