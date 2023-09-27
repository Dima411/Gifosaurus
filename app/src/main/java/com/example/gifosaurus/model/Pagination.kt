package com.example.gifosaurus.model

/**
 * Model for Pagination
 */
data class Pagination(
    val offset: Int,
    val total_count: Int,
    val count: Int
)
