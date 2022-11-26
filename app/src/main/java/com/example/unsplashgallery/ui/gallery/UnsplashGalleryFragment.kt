package com.example.unsplashgallery.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.unsplashgallery.R
import com.example.unsplashgallery.data.UnsplashImage
import com.example.unsplashgallery.databinding.UnsplashGalleryFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UnsplashGalleryFragment : Fragment(R.layout.unsplash_gallery_fragment), MenuProvider, UnsplashImageAdapter.OnItemClickListener {

    private val galleryViewModel by viewModels<UnsplashGalleryViewModel>()
    private var _binding: UnsplashGalleryFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.addMenuProvider(this, viewLifecycleOwner)

        _binding = UnsplashGalleryFragmentBinding.bind(view)

        val adapter = UnsplashImageAdapter(this)

        binding.apply {
            unsplashGalleryRecyclerView.setHasFixedSize(true)
            unsplashGalleryRecyclerView.adapter =
                adapter.withLoadStateFooter(UnsplashImageLoadStateAdapter { adapter.retry() })
            unsplashGalleryRetryButton.setOnClickListener {
                adapter.retry()
            }
        }

        galleryViewModel.images.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener {
            binding.apply {
                unsplashGalleryProgressBar.isVisible = it.source.refresh is LoadState.Loading
                unsplashGalleryRecyclerView.isVisible = it.source.refresh is LoadState.NotLoading
                unsplashGalleryRetryButton.isVisible = it.source.refresh is LoadState.Error
                unsplashGalleryErrorTextView.isVisible = it.source.refresh is LoadState.Error

                if (it.source.refresh is LoadState.NotLoading &&
                    it.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    unsplashGalleryRecyclerView.isVisible = false
                    unsplashGalleryEmptyResponseTextView.isVisible = true
                } else {
                    unsplashGalleryEmptyResponseTextView.isVisible = false
                }
            }
        }
    }

    override fun onItemClick(image: UnsplashImage) {
        val action = UnsplashGalleryFragmentDirections.actionUnsplashGalleryFragmentToUnsplashImageDetails(image)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_menu_unsplash_gallery, menu)

        val searchItem = menu.findItem(R.id.unsplash_gallery_search_menu)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.unsplashGalleryRecyclerView.scrollToPosition(0)
                    galleryViewModel.changeImageSearchQuery(query)
                    searchView.clearFocus()
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}