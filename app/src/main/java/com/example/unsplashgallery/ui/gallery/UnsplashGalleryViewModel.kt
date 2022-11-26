package com.example.unsplashgallery.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.unsplashgallery.data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UnsplashGalleryViewModel @Inject constructor(
    private val unsplashRepository: UnsplashRepository
) : ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = "Cat"
    }

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val images = currentQuery.switchMap {
        unsplashRepository.getSearchResults(it).cachedIn(viewModelScope)
    }

    fun changeImageSearchQuery(query: String) {
        currentQuery.value = query
    }

}