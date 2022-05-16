package nl.farid.android.photoalbum.presentation.view.favorites

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import nl.farid.android.photoalbum.R
import nl.farid.android.photoalbum.databinding.FragmentFavoriteBinding
import nl.farid.android.photoalbum.presentation.view.albums.ID
import nl.farid.android.photoalbum.presentation.view.favorites.adapter.FavoritesAdapter
import nl.farid.android.photoalbum.util.launchAndRepeatWithViewLifecycle

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private val favoriteViewModel: FavoritesViewModel by viewModels()
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFavoriteBinding.bind(view)

        setupRecyclerView(binding)
        initViews()
        subscribeObservers(binding)

        favoriteViewModel.getAlbumsFromCache()
    }

    private fun setupRecyclerView(binding: FragmentFavoriteBinding) {
        favoritesAdapter = FavoritesAdapter()
        binding.favoritesRecyclerview.apply {
            adapter = favoritesAdapter
            layoutManager = LinearLayoutManager(
                requireActivity(),
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
        }

        val animator = binding.favoritesRecyclerview.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
    }

    private fun initViews() {
        favoritesAdapter.setOnItemClickListener { album ->
            val bundle = bundleOf(ID to album.id)
            findNavController().navigate(R.id.action_favoriteFragment_to_photoFragment, bundle)
        }

        favoritesAdapter.setOnDeleteClickListener { album ->
            favoriteViewModel.deleteAlbum(album.id)
        }
    }

    private fun subscribeObservers(binding: FragmentFavoriteBinding) {
        launchAndRepeatWithViewLifecycle {
            favoriteViewModel.uiState.collectLatest { state ->
                if(state.isLoading){
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
                state.albums.collectLatest { albums ->
                    favoritesAdapter.setData(albums)
                }
            }
        }
    }
}