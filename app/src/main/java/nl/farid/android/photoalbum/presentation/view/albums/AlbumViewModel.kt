package nl.farid.android.photoalbum.presentation.view.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import nl.farid.android.photoalbum.business.usecase.abstraction.*
import nl.farid.android.photoalbum.model.app_model.Album
import nl.farid.android.photoalbum.util.AlbumManager
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel
@Inject constructor(
    private val iGetAlbums: IGetAlbums,
    private val albumManager: AlbumManager
): ViewModel(){

    val uiState: StateFlow<AlbumState> = MutableStateFlow(AlbumState())

    private fun setState(block: AlbumState.() -> AlbumState) {
        (uiState as MutableStateFlow).value = block(uiState.value)
    }

    fun getAlbums(){
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            when(val result = iGetAlbums.getAlbums()){
                is GetAlbumResult.Error -> {
                    setState { copy(isLoading = false, error = result.e) }
                }
                is GetAlbumResult.Success -> {
                    setState { copy(isLoading = false, albums = result.list) }
                }
            }
        }
    }

    fun setSelectedAlbum(album: Album){
        albumManager.setAlbum(album)
    }
}

data class AlbumState(
    val isLoading: Boolean = false,
    val albums: List<Album> = emptyList(),
    val error: Throwable? = null
)