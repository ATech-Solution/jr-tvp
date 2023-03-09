package com.atech.domain.entities

import com.atech.domain.entities.ReviewModel


data class ReviewsModel(
    val id: Int?,
    val page: Int?,
    val results: List<ReviewModel>?,
    val totalPages: Int?,
    val totalResults: Int?
)