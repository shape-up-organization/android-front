package com.shapeup.api.services.users

import kotlinx.serialization.Serializable

data class GetRankPayload(
    val type: RankType,
    val page: Int? = 0,
    val size: Int? = 6
)

data class GetUserPayload(
    val username: String
)

@Serializable
data class SearchByUsernamePayload(
    val username: String
)

@Serializable
data class SearchByFullNamePayload(
    val fullName: String
)

@Serializable
data class UserFieldPayload(
    val email: String,
    val name: String,
    val lastName: String,
    val cellPhone: String,
    val birth: String,
    val biography: String,
    val username: String,
    val password: String,
)

enum class RankType(val value: String) {
    GLOBAL(value = "global"),
    FRIENDS(value = "friends")
}