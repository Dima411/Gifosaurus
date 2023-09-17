package com.example.gifosaurus.api

import com.example.gifosaurus.model.GifResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  Інтерфейс, який визначає два запити до Giphy API - один для отримання популярних GIF-зображень
 *  і один для пошуку GIF-зображень
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