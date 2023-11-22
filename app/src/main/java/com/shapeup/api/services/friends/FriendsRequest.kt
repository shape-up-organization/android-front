package com.shapeup.api.services.friends

data class RequestFriendshipPayload(
    val newFriendUsername: String
)

data class AcceptFriendshipPayload(
    val friendUsername: String
)

data class DeleteFriendshipPayload(
    val friendUsername: String
)

data class DeleteFriendPayload(
    val friendUsername: String
)