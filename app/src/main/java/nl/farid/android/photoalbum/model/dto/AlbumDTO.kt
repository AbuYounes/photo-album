package nl.farid.android.photoalbum.model.dto

import nl.farid.android.photoalbum.model.app_model.Album

data class AlbumDTO(
    val id: Int,
    val title: String,
    val userId: Int
) {

    fun toAlbum(): Album {
        return Album(
            id = id,
            title = title,
            userId = userId
        )
    }
}