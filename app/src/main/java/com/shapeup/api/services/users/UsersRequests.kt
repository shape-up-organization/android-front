package com.shapeup.api.services.users

import kotlinx.serialization.Serializable

@Serializable
data class SearchUsersPayload(
    val nameOrUsername: String
)

data class GetRankPayload(
    val type: RankType,
    val page: Int? = 0,
    val size: Int? = 6
)

enum class RankType(val value: String) {
    GLOBAL(value = "global"),
    FRIENDS(value = "friends")
}