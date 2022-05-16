package nl.farid.android.photoalbum.model.app_model

import nl.farid.android.photoalbum.model.entity.AlbumEntity

data class Album(
    val id: Int,
    val title: String,
    val userId: Int,
    val isFavorite: Boolean
) {
    fun toAlbumEntity(): AlbumEntity {
        return AlbumEntity(
            id = id,
            title = title,
            userId = userId
        )
    }
}