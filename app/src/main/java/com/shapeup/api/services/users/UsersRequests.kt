package com.shapeup.api.services.users

import kotlinx.serialization.Serializable

@Serializable
data class SearchByUsernamePayload(
    val username: String
)

@Serializable
data class SearchByFullNamePayload(
    val fullName: String
)

data class GetRankPayload(
    val type: RankType,
    val page: Int? = 0,
    val size: Int? = 6
)

data class GetUserPayload(
    val username: String
)

enum class RankType(val value: String) {
    GLOBAL(value = "global"),
    FRIENDS(value = "friends")
}