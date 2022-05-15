package nl.farid.android.photoalbum.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import nl.farid.android.photoalbum.model.app_model.Album
import nl.farid.android.photoalbum.model.dto.AlbumDTO

@Entity(tableName = "album")
data class AlbumEntity(
    @PrimaryKey
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

    fun toAlbumList(listEntity: List<AlbumEntity>): List<Album> {
        val list: MutableList<Album> = mutableListOf()
        listEntity.forEach {
            list.add(it.toAlbum())
        }

        return list
    }

}