package com.projects.base.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.projects.base.data.model.WallpaperItem
import com.projects.base.databinding.ItemWallpaperBinding

internal class WallpaperAdapter(
    private val onClick: (WallpaperItem) -> Unit
) : ListAdapter<WallpaperItem, WallpaperViewHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<WallpaperItem>() {
            override fun areItemsTheSame(oldItem: WallpaperItem, newItem: WallpaperItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: WallpaperItem, newItem: WallpaperItem): Boolean =
                oldItem.url == newItem.url
        }
    }

    fun submit(list: List<WallpaperItem>) {
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val binding = ItemWallpaperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WallpaperViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class WallpaperViewHolder(
    private val binding: ItemWallpaperBinding,
    private val onClick: (WallpaperItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WallpaperItem) {
        Glide.with(binding.image.context)
            .load(item.url)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.image)
        binding.root.setOnClickListener { onClick(item) }
    }
}