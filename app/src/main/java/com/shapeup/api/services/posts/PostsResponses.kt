package com.shapeup.api.services.posts

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val countComments: Int,
    val countLike: Int,
    val createdAt: String,
    val description: String? = null,
    val id: String,
    val liked: Boolean = false,
    val photoUrls: List<String>,
    val username: String
)