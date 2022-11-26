package com.example.unsplashgallery.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashgallery.databinding.UnsplashImageLoadStateFooterBinding

class UnsplashImageLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<UnsplashImageLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = UnsplashImageLoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: UnsplashImageLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.loadStateRetryButton.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                loadStateProgressBar.isVisible = loadState is LoadState.Loading
                loadStateRetryButton.isVisible = loadState !is LoadState.Loading
                loadStateErrorTextView.isVisible = loadState !is LoadState.Loading
            }
        }
    }

}