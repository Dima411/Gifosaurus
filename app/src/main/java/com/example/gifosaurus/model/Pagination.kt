package com.example.gifosaurus.model

/**
 * Model for Pagination
 *
 * Представлення даних про пагінацію
 */
data class Pagination(
    val offset: Int,
    val total_count: Int,
    val count: Int
)
