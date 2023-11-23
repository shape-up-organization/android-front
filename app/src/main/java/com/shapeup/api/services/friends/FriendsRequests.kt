package com.shapeup.api.services.friends

import kotlinx.serialization.Serializable

@Serializable
data class RequestFriendshipPayload(
    val newFriendUsername: String
)

@Serializable
data class AcceptFriendshipPayload(
    val friendUsername: String
)

@Serializable
data class DeleteFriendshipPayload(
    val friendUsername: String
)

@Serializable
data class DeleteFriendPayload(
    val friendUsername: String
)