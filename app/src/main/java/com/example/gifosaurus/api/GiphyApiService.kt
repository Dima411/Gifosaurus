package com.example.gifosaurus.api

import com.example.gifosaurus.network.model.GifResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  An interface that defines two requests to the Giphy API - one for getting popular GIF images
 *  and one for searching GIF images.
 */
interface GiphyApiService {
    @GET("v1/gifs/trending")
    suspend fun getTrendingGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): GifResponse

    @GET("v1/gifs/search")
    suspend fun searchGifs(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): GifResponse
}