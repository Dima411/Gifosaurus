package com.example.gifosaurus.network.model

/**
 * Model for Pagination
 */
data class GifPage(
    val offset: Int,
    val total_count: Int,
    val count: Int
)
