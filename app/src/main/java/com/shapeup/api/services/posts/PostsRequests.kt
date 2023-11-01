package com.shapeup.api.services.posts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class GetPostsPaginatedPayload(
    val page: Int? = 0,
    val size: Int? = 6
)

data class GetPostByIdPayload(
    val postId: String
)

data class GetCommentsByPostsIdPaginatedPayload(
    val postId: String,
    val page: Int? = 0,
    val size: Int? = 20
)

@Serializable
data class SendCommentPayload(
    @SerialName("post_id") val postId: String,
    @SerialName("comment_message") val commentMessage: String,
)

data class ToggleLikePayload(
    val postId: String
)

data class DeletePostPayload(
    val postId: String
)

data class CreatePostPayload(
    val description: String? = null,
    val files: List<ByteArray?>
)

@Serializable
data class CreatePostWithoutFilePayload(
    val description: String
)