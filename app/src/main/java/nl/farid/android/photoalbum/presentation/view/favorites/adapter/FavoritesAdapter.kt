package nl.farid.android.photoalbum.presentation.view.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import nl.farid.android.photoalbum.databinding.RowFavoritesBinding
import nl.farid.android.photoalbum.model.app_model.Album

class FavoritesAdapter: RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {
    private var photoAlbums: List<Album> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = RowFavoritesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)

        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val item = photoAlbums[position]
        with(holder){
            binding.title.text = item.title
        }

        holder.binding.title.setOnClickListener {
            notifyItemChanged(position)
            onClickListener?.let { it(item) }
        }

        holder.binding.deleteBtn.setOnClickListener {
            notifyItemChanged(position)
            onDeleteClickListener?.let { it(item) }
        }
    }

    override fun getItemCount(): Int = photoAlbums.size

    fun setData(list: List<Album>) {
        val diffUtil = FavoritesDiffUtil(photoAlbums, list)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        photoAlbums = list
        diffResults.dispatchUpdatesTo(this)
    }

    private var onClickListener: ((Album) -> Unit)? = null

    fun setOnItemClickListener(listener: (Album) -> Unit) {
        onClickListener = listener
    }

    private var onDeleteClickListener: ((Album) -> Unit)? = null

    fun setOnDeleteClickListener(listener: (Album) -> Unit) {
        onDeleteClickListener = listener
    }

    inner class FavoritesViewHolder(val binding: RowFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root)
}