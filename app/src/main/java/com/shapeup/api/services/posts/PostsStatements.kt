package com.shapeup.api.services.posts

import com.shapeup.ui.viewModels.logged.Comment
import io.ktor.http.HttpStatusCode

data class GetPostsPaginatedStatement(
    val data: List<Post>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class GetPostByIdStatement(
    val data: Post? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class GetCommentsByPostsIdPaginatedStatement(
    val data: List<Comment>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class SendCommentStatement(
    val content: String? = null,
    val status: HttpStatusCode
)

data class ToggleLikeStatement(
    val content: String? = null,
    val status: HttpStatusCode
)

data class DeletePostStatement(
    val content: String? = null,
    val status: HttpStatusCode
)

data class CreatePostStatement(
    val content: String? = null,
    val status: HttpStatusCode
)