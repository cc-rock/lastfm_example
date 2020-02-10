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

class ArtistSearchAdapter: RecyclerView.Adapter<ArtistSearchAdapter.ArtistSearchViewHolder>() {

    companion object {
        const val LOAD_MORE_THRESHOLD = 5
    }

    private var items: List<ArtistSearchItem> = emptyList()
    private var totalCount = 0

    var listener: Listener? = null

    var moreDataRequested = false

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistSearchViewHolder {
        return ArtistSearchViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.artist_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArtistSearchViewHolder, position: Int) {
        // paylods version used
    }

    override fun onBindViewHolder(holder: ArtistSearchViewHolder, position: Int, payloads: List<Any>) {
        holder.bindItem(items[position])
    }

    fun updateData(newItems: List<ArtistSearchItem>, newTotalCount: Int) {
        val oldSize = items.size
        items = newItems
        if (oldSize >= items.size || oldSize == 0) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeChanged(0, oldSize - 1, null)
            notifyItemRangeInserted(oldSize, items.size - 1)
        }
        totalCount = newTotalCount
        moreDataRequested = false
    }

    class ArtistSearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val artistName: TextView = itemView.findViewById(R.id.artist_name)
        private var item: ArtistSearchItem? = null

        fun bindItem(newItem: ArtistSearchItem) {
            if (newItem != item) {
                item = newItem
                artistName.text = "${adapterPosition} ${newItem.name}"
                if (!newItem.imageUrl.isNullOrEmpty()) {
                    image.visibility = View.VISIBLE
                    Picasso.get().load(newItem.imageUrl).into(image)
                } else {
                    image.visibility = View.INVISIBLE
                }
            }
        }
    }

    interface Listener {
        fun onMoreDataNeeded()
    }

}