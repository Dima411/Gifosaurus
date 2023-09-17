package com.example.gifosaurus.model

/**
 * Клас даних, який представляє структуру даних, що повертається Giphy API.
 */
data class GifResponse(
    val data: List<Gif>
)
