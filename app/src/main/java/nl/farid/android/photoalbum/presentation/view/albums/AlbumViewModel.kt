package nl.farid.android.photoalbum.presentation.view.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import nl.farid.android.photoalbum.business.usecase.abstraction.*
import nl.farid.android.photoalbum.model.app_model.Album
import nl.farid.android.photoalbum.util.AlbumManager
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel
@Inject constructor(
    private val iGetAlbums: IGetAlbums,
    private val albumManager: AlbumManager
): ViewModel(){

    private val _effect: Channel<ErrorState> = Channel()
    val effect = _effect.receiveAsFlow()

    private fun setEffect(builder: () -> ErrorState) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    val uiState: StateFlow<AlbumState> = MutableStateFlow(AlbumState())

    private fun setState(block: AlbumState.() -> AlbumState) {
        (uiState as MutableStateFlow).value = block(uiState.value)
    }

    fun getAlbums(){
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            when(val result = iGetAlbums.getAlbums()){
                is GetAlbumResult.Error -> {
                    setState { copy(isLoading = false) }
                    setEffect { ErrorState(result.e) }
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
)

data class ErrorState(
    val error: Exception? = null
)