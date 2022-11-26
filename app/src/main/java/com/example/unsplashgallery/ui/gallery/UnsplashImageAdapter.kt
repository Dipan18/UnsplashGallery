package com.example.unsplashgallery.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.unsplashgallery.R
import com.example.unsplashgallery.data.UnsplashImage
import com.example.unsplashgallery.databinding.ItemUnsplashImageBinding

class UnsplashImageAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<UnsplashImage, UnsplashImageAdapter.UnsplashImageViewHolder>(IMAGE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsplashImageViewHolder {
        val binding =
            ItemUnsplashImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return UnsplashImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UnsplashImageViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class UnsplashImageViewHolder(private val binding: ItemUnsplashImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(image: UnsplashImage) {
            binding.apply {
                Glide.with(itemView)
                    .load(image.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(unsplashImageImageView)

                usernameTextView.text = image.user.username
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(image: UnsplashImage)
    }

    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashImage>() {
            override fun areItemsTheSame(oldItem: UnsplashImage, newItem: UnsplashImage) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UnsplashImage, newItem: UnsplashImage) =
                oldItem == newItem
        }
    }

}