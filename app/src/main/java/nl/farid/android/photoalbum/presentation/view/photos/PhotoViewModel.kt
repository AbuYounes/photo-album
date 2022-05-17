package nl.farid.android.photoalbum.presentation.view.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.farid.android.photoalbum.business.usecase.abstraction.*
import nl.farid.android.photoalbum.model.app_model.Album
import nl.farid.android.photoalbum.model.app_model.Photo
import nl.farid.android.photoalbum.util.AlbumManager
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel
@Inject constructor(
    private val iGetPhotosFromAlbum: IGetPhotosFromAlbum,
    private val iMarkAlbumAsFavorite: IMarkAlbumAsFavorite,
    private val iGetAlbumsFromCache: IGetAlbumsFromCache,
    private val iDeleteAlbum: IDeleteAlbum,
    private val albumManager: AlbumManager,
) : ViewModel() {

    val uiState: StateFlow<PhotoState> = MutableStateFlow(PhotoState())

    private fun setState(block: PhotoState.() -> PhotoState) {
        (uiState as MutableStateFlow).value = block(uiState.value)
    }

    fun getPhotosOfAlbum(albumId: Int){
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            when(val result = iGetPhotosFromAlbum.getPhotoFromAlbum(albumId)){
                is GetPhotosFromAlbumResult.Error -> {
                    setState { copy(isLoading = false, error = result.e ) }
                }
                is GetPhotosFromAlbumResult.Success -> {
                    setState { copy(isLoading = false, photos = result.list) }
                }
            }
        }
    }

    fun setFavoriteAlbum(album: Album){
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            when(val result = iMarkAlbumAsFavorite.markAsFavorite(album)){
                is MarkAlbumAsFavoriteResult.Error -> {
                    setState { copy(isLoading = false, error = result.e) }
                }
                MarkAlbumAsFavoriteResult.Success -> {
                    setState { copy(isLoading = false, isMarkedAsFavorite = true ) }
                }
            }
        }
    }

    fun deleteAlbum(albumId: Int){
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            when(val result = iDeleteAlbum.deleteAlbum(albumId)){
                is DeleteAlbumResult.Error -> {
                    setState { copy(isLoading = false, error = result.e) }
                }
                DeleteAlbumResult.Success -> {
                    setState { copy(isLoading = false, isMarkedAsFavorite = false ) }
                }
            }
        }
    }

    fun getAlbumsFromCache(){
        viewModelScope.launch {
            when(val result = iGetAlbumsFromCache.getAllAlbumsFromCache()){
                is GetAlbumsFromCacheResult.Error -> {
                    setState { copy(isLoading = false, error = result.e) }
                }
                is GetAlbumsFromCacheResult.Success -> {
                    result.list.forEach {
                        if(it.id == albumManager.getAlbum().id){
                            setState { copy(isMarkedAsFavorite = true ) }
                        }
                    }
                }
            }
        }
    }

    fun getSelectedAlbum(){
        setState { copy(album = albumManager.getAlbum()) }
    }
}

data class PhotoState(
    val isLoading: Boolean = false,
    val photos: List<Photo> = emptyList(),
    val isMarkedAsFavorite: Boolean = false,
    val album: Album? = null,
    val error: Throwable? = null
)