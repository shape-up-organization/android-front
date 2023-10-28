package com.shapeup.api.services.posts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    val id : String,
    val description : String,
    @SerialName("created_at")
    val createdAt : String,
    @SerialName("count_like")
    val countLike : Int,
    @SerialName("count_comments")
    val countComments : Int,
    @SerialName("photo_urls")
    val photoUrls : List<String>,
    @SerialName("is_liked")
    val isLiked : Boolean,
    val username : String,
    @SerialName("profile_picture")
    val profilePicture : String,
    @SerialName("first_name")
    val firstName : String,
    @SerialName("last_name")
    val lastName : String,
    val xp : Long
)

@Serializable
data class CommentResponse(
    @SerialName("comment_id")
    val commentId : String,
    @SerialName("comment_message")
    val commentMessage : String,
    @SerialName("created_at")
    val createdAt : String,
    val username : String,
    @SerialName("profile_picture")
    val profilePicture : String,
    @SerialName("first_name")
    val firstName : String,
    @SerialName("last_name")
    val lastName : String,
    val xp : Long
)
