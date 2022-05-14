package nl.farid.android.photoalbum.business.usecase.implementation

import nl.farid.android.photoalbum.business.repository.abstraction.IPhotoAlbumRepository
import nl.farid.android.photoalbum.business.usecase.abstraction.IMarkAlbumAsFavorite
import javax.inject.Inject

class MarkAlbumAsFavorite
@Inject constructor(
    private val iPhotoAlbumRepository: IPhotoAlbumRepository
): IMarkAlbumAsFavorite {
}