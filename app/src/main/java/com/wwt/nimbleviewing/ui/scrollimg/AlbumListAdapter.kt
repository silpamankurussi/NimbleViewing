package com.wwt.nimbleviewing.ui.scrollimg

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wwt.nimbleviewing.data.Album
import com.wwt.nimbleviewing.databinding.ListItemAlbumBinding

class AlbumListAdapter : RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder>() {

    private var albumList: List<Album> = listOf()

    fun submitList(albums: List<Album>) {
        this.albumList = albums
    }

    inner class AlbumViewHolder(private val binding: ListItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            binding.title = album.title
            binding.url = album.url
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val listItemBinding = ListItemAlbumBinding.inflate(inflater, parent, false)
        return AlbumViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) =
        holder.bind(albumList[position])


    override fun getItemCount() =
        albumList.size

}


