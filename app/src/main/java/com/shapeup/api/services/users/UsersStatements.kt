package com.shapeup.api.services.users

import io.ktor.http.HttpStatusCode

data class SearchUsersStatement(
    val data: List<UserSearch>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class GetRankStatement(
    val data: List<UserRank>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)