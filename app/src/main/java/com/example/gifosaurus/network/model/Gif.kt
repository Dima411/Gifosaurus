package com.example.gifosaurus.network.model

/**
 * Клас даних, який представляє структуру даних, що повертається Giphy API.
 */
data class Gif (
    val id: String,
    val url: String,
    val title: String
)
