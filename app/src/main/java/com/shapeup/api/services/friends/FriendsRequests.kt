package com.shapeup.api.services.friends

import kotlinx.serialization.Serializable

@Serializable
data class RequestFriendshipPayload(
    val username: String
)

@Serializable
data class AcceptFriendshipRequestPayload(
    val username: String
)

@Serializable
data class DeleteFriendshipRequestPayload(
    val username: String
)

@Serializable
data class DeleteFriendPayload(
    val username: String
)