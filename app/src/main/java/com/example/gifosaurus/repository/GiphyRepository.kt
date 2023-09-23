package com.example.gifosaurus.repository

import com.example.gifosaurus.api.GiphyApiService
import com.example.gifosaurus.model.GifResponse
import retrofit2.http.Query

/**
 * Клас, який використовує GiphyApiService для виконання запитів до API.
 */
class GiphyRepository(private val apiService: GiphyApiService) {
    suspend fun searchGifs(
        apiKey: String,
        query: String,
        limit: Int,
        offset: Int
    ): GifResponse {
        return apiService.searchGifs(
            apiKey = apiKey,
            query = query,
            limit = limit,
            offset = offset
        )
    }

    suspend fun getTrendingGifs(
        apiKey: String,
        limit: Int,
        offset: Int
    ): GifResponse {
        return apiService.getTrendingGifs(
            apiKey = apiKey,
            limit = limit,
            offset = offset)
    }
}
