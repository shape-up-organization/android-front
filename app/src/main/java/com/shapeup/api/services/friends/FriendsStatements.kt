package com.shapeup.api.services.friends

import io.ktor.http.HttpStatusCode

data class RequestFriendshipStatement(
    val data: RequestFriendship? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class AcceptFriendshipStatement(
    val data: AcceptFriendship? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class GetAllFriendshipStatement(
    val data: List<Friendship>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class DeleteFriendshipStatement(
    val content: String? = null,
    val status: HttpStatusCode
)

data class DeleteFriendStatement(
    val content: String? = null,
    val status: HttpStatusCode
)