package com.example.unsplashgallery.api

import com.example.unsplashgallery.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val ACCESS_KEY = BuildConfig.UNSPLASH_ACCESS_KEY
    }

    @Headers("Accept-Version: v1", "Authorization: Client-ID $ACCESS_KEY")
    @GET("search/photos")
    suspend fun searchImages(
        @Query("query") query: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") resultsPerPage: Int
    ): UnsplashResponse
}