package com.example.unsplashgallery.api

import com.example.unsplashgallery.data.UnsplashImage

data class UnsplashResponse(
    val results: List<UnsplashImage>
)