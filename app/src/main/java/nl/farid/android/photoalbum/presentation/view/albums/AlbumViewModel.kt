package nl.farid.android.photoalbum.presentation.view.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.farid.android.photoalbum.business.usecase.abstraction.GetAlbumResult
import nl.farid.android.photoalbum.business.usecase.abstraction.IGetAlbums
import nl.farid.android.photoalbum.business.usecase.abstraction.IMarkAlbumAsFavorite
import nl.farid.android.photoalbum.business.usecase.abstraction.MarkAlbumAsFavoriteResult
import nl.farid.android.photoalbum.model.app_model.Album
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel
@Inject constructor(
    private val iGetAlbums: IGetAlbums,
    private val iMarkAlbumAsFavorite: IMarkAlbumAsFavorite
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

    fun setFavoriteAlbum(album: Album){
        setState { copy(isLoading = true) }

        viewModelScope.launch {
            when(val result = iMarkAlbumAsFavorite.markAsFavorite(album)){
                is MarkAlbumAsFavoriteResult.Error -> {
                    setState { copy(isLoading = false, error = result.e) }
                }
                MarkAlbumAsFavoriteResult.Success -> {
                    setState { copy(isLoading = false, isMarkedAsFavorite = true) }
                }
            }
        }
    }
}

data class AlbumState(
    val isLoading: Boolean = false,
    val isMarkedAsFavorite: Boolean = false,
    val albums: List<Album> = emptyList(),
    val error: Throwable? = null
)