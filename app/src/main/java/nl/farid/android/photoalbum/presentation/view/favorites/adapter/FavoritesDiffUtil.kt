package nl.farid.android.photoalbum.presentation.view.favorites.adapter

import androidx.recyclerview.widget.DiffUtil
import nl.farid.android.photoalbum.model.app_model.Album

class FavoritesDiffUtil (
    private val oldList: List<Album>,
    private val newList: List<Album>
): DiffUtil.Callback()  {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        when {
            oldList[oldItemPosition].id!= newList[newItemPosition].id -> {
                false
            }

            else -> {
                true
            }
        }
}