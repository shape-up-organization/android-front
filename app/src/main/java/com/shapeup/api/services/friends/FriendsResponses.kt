package com.shapeup.api.services.friends

import kotlinx.serialization.Serializable

@Serializable
data class RequestFriendship(
    val id: String,
    val usernameSender: String,
    val usernameReceiver: String,
    val accepted: Boolean,
)

@Serializable
data class AcceptFriendship(
    val id: String,
    val usernameSender: String,
    val usernameReceiver: String,
    val accepted: Boolean,
)

@Serializable
data class FriendBase(
    val id: String,
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val username: String,
    val xp: Int,
    val profilePicture: String? = null,
    val cellPhone: String
)