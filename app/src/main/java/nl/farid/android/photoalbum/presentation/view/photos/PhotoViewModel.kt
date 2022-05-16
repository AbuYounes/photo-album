package nl.farid.android.photoalbum.presentation.view.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.farid.android.photoalbum.business.usecase.abstraction.GetPhotosFromAlbumResult
import nl.farid.android.photoalbum.business.usecase.abstraction.IGetPhotosFromAlbum
import nl.farid.android.photoalbum.model.app_model.Photo
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel
@Inject constructor(
    private val iGetPhotosFromAlbum: IGetPhotosFromAlbum
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
}

data class PhotoState(
    val isLoading: Boolean = false,
    val photos: List<Photo> = emptyList(),
    val error: Throwable? = null
)