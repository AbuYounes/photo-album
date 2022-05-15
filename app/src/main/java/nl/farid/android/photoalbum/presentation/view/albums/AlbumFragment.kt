package nl.farid.android.photoalbum.presentation.view.albums

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.farid.android.photoalbum.R
import nl.farid.android.photoalbum.databinding.FragmentAlbumBinding
import nl.farid.android.photoalbum.presentation.view.favorites.FavoritesViewModel
import nl.farid.android.photoalbum.presentation.view.photos.PhotoViewModel

@AndroidEntryPoint
class AlbumFragment : Fragment(R.layout.fragment_album) {
    private val albumViewModel: AlbumViewModel by viewModels()
    private val photoViewModel: PhotoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAlbumBinding.bind(view)

        initViews(binding)
        subscribeObservers(binding)
    }

    private fun initViews(binding: FragmentAlbumBinding) {
        binding.button1.setOnClickListener {
            albumViewModel.getAlbums()
        }

        binding.button2.setOnClickListener {
            photoViewModel.getPhotosOfAlbum(1)
        }
    }

    private fun subscribeObservers(binding: FragmentAlbumBinding) {
        launchAndRepeatWithViewLifecycle {
            albumViewModel.uiState.collect {
                it.albums.forEach { album ->
                    Log.d("ALBUMS", "subscribeObservers ID: ${album.id}")
                }
            }
        }

        launchAndRepeatWithViewLifecycle {
            photoViewModel.uiState.collect {
                it.photos.forEach { photo ->
                    Log.d("PHOTOS", "subscribeObservers ID: ${photo.id}")
                }
            }
        }

    }

}

/** Pauses the Collect and Emit and unregisters the consumer when the view goes to the STOPPED state **/
inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}