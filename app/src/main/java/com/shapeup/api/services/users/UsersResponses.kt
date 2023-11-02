package com.shapeup.api.services.users

import com.shapeup.ui.viewModels.logged.FriendshipStatus
import kotlinx.serialization.Serializable

@Serializable
data class UserSearch(
    val biography: String? = null,
    val firstName: String,
    val lastName: String,
    val profilePicture: String? = null,
    val username: String,
    val xp: Int,
    val friendshipStatus: FriendshipStatus? = null
)

@Serializable
data class UserRank(
    val firstName: String,
    val lastName: String,
    val profilePicture: String? = null,
    val username: String,
    val xp: Int,
)