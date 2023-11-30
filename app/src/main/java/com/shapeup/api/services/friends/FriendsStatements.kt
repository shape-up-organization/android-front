package com.shapeup.api.services.friends

import io.ktor.http.HttpStatusCode

data class GenericFriendshipStatement(
    val status: HttpStatusCode
)

data class RequestFriendshipStatement(
    val data: RequestFriendship? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class AcceptFriendshipRequestStatement(
    val data: AcceptFriendship? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class GetAllFriendshipStatement(
    val data: List<FriendBase>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class DeleteFriendshipRequestStatement(
    val content: String? = null,
    val status: HttpStatusCode
)

data class DeleteFriendStatement(
    val content: String? = null,
    val status: HttpStatusCode
)