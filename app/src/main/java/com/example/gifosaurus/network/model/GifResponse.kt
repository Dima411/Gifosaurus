package com.example.gifosaurus.network.model

/**
 * Клас даних, який представляє структуру даних, що повертається Giphy API.
 */
data class GifResponse(
    val data: List<Gif>
)
