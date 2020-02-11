package com.example.lastfm.search.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lastfm.R
import com.example.lastfm.common.data.entities.ArtistSearchItem
import com.squareup.picasso.Picasso

class ArtistSearchAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_ARTIST = 0
        private const val TYPE_LOADING = 1

        private const val LOAD_MORE_THRESHOLD = 10
    }

    private var items: List<ArtistSearchItem> = emptyList()
    private var totalCount = 0
    private var loading = false

    var listener: Listener? = null

    var moreDataRequested = false

    override fun getItemCount(): Int = items.size + 1

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            items.size -> TYPE_LOADING
            else -> TYPE_ARTIST
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ARTIST -> ArtistSearchViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.artist_list_item, parent, false)
            )
            else -> LoadingViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.artist_list_loading_item, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // paylods version used
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        when(holder) {
            is ArtistSearchViewHolder -> holder.bindItem(items[position])
            is LoadingViewHolder -> holder.itemView.visibility = if (loading) View.VISIBLE else View.GONE
        }
    }

    fun updateData(newItems: List<ArtistSearchItem>, newTotalCount: Int, newLoading: Boolean) {
        // This method should be done better, using the DiffUtil class to fire only
        // the item updates that are really needed, but for time reasons I did it like this
        val oldSize = itemCount
        items = newItems
        loading = newLoading
        if (oldSize >= itemCount || oldSize == 0) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeChanged(0, oldSize, "")
            notifyItemRangeInserted(oldSize, itemCount - oldSize)
        }
        totalCount = newTotalCount
        if (!newLoading) {
            moreDataRequested = false
        }
    }

    fun onScrolledDown(lastVisiblePosition: Int) {
        if (!moreDataRequested && items.size < totalCount && (items.size - lastVisiblePosition) < LOAD_MORE_THRESHOLD) {
            listener?.onMoreDataNeeded()
            moreDataRequested = true
        }
    }

    class ArtistSearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val artistName: TextView = itemView.findViewById(R.id.artist_name)
        private var item: ArtistSearchItem? = null

        fun bindItem(newItem: ArtistSearchItem) {
            if (newItem != item) {
                item = newItem
                artistName.text = newItem.name
                if (!newItem.imageUrl.isNullOrEmpty()) {
                    image.visibility = View.VISIBLE
                    Picasso.get().load(newItem.imageUrl).into(image)
                } else {
                    image.visibility = View.INVISIBLE
                }
            }
        }
    }

    class LoadingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    interface Listener {
        fun onMoreDataNeeded()
    }

}