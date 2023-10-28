package com.shapeup.api.services.posts

import io.ktor.http.HttpStatusCode

data class PostStatement(
    val data: List<PostResponse>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)