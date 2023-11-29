package com.shapeup.api.services.users

import io.ktor.http.HttpStatusCode

data class DeleteByEmailStatement(
    val content: String? = null,
    val status: HttpStatusCode
)

data class UserFieldStatement(
    val data: List<UserFieldUpdate>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class GetRankStatement(
    val data: List<UserRank>? = null,
)

data class SearchUsersStatement(
    val data: List<UserSearch>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class SearchByUsernameStatement(
    val data: SearchByUsername? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class GetUserStatement(
    val data: UserSearch? = null,
)

data class GetUserXpStatement(
    val data: Long? = null,
    val content: String? = null,
    val status: HttpStatusCode
)