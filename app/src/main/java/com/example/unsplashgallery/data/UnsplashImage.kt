package com.example.unsplashgallery.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnsplashImage(
    val id: String,
    val description: String?,
    val urls: UnsplashImageUrls,
    val user: UnsplashUser
) : Parcelable {

    @Parcelize
    class UnsplashImageUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String,
    ) : Parcelable

    @Parcelize
    data class UnsplashUser(
        val name: String,
        val username: String,
    ) : Parcelable {
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageGallery&utm_medium=referral"
    }
}