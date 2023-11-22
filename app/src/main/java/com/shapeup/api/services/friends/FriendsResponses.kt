package com.shapeup.api.services.friends

data class RequestFriendship(
    val id: String,
    val usernameSender: String,
    val usernameReceiver: String,
    val accepted: Boolean,
)

data class AcceptFriendship(
    val id: String,
    val usernameSender: String,
    val usernameReceiver: String,
    val accepted: Boolean,
)

data class Friendship(
    val id: String,
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val username: String,
    val xp: Long,
    val profilePicture: String,
)