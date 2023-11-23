package com.shapeup.api.services.users

import kotlinx.serialization.Serializable

@Serializable
data class UserFieldUpdate(
    val token: String,
    val updatedAt: String
)

@Serializable
data class SearchByUsername(
    val firstName: String,
    val lastName: String,
    val username: String,
    val profilePicture: String,
    val biography: String,
    val xp: Long,
    val friendshipStatus: FriendshipSearchStatus,
)

@Serializable
data class FriendshipSearchStatus(
    val haveFriendRequest: Boolean,
    val isFriend: Boolean,
    val userSenderFriendshipRequest: String
)