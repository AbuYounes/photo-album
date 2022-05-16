package nl.farid.android.photoalbum.presentation.view.photos.adapter

import androidx.recyclerview.widget.DiffUtil
import nl.farid.android.photoalbum.model.app_model.Photo

class PhotoDiffUtil (
    private val oldList: List<Photo>,
    private val newList: List<Photo>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }

            else -> {
                true
            }
        }
}