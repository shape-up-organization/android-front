package com.shapeup.api.services.users

import com.shapeup.ui.viewModels.logged.FriendshipStatus

data class UserFieldUpdate(
    val token: String,
    val updatedAt: String
)

data class SearchByUsername(
    val firstName: String,
    val lastName: String,
    val username: String,
    val profilePicture: String,
    val biography: String,
    val xp: Long,
    val friendshipStatus: FriendshipSearchStatus,
)

data class FriendshipSearchStatus(
    val haveFriendRequest: Boolean,
    val isFriend: Boolean,
    val userSenderFriendshipRequest: String
)