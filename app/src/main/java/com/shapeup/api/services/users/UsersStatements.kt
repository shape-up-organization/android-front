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

data class SearchUsersStatement(
    val data: List<SearchByUsername>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class SearchByUsernameStatement(
    val data: SearchByUsername? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class GetUserXpStatement(
    val data: Long? = null,
    val content: String? = null,
    val status: HttpStatusCode
)