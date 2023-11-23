package com.shapeup.api.services.rank

import kotlinx.serialization.Serializable

@Serializable
data class GetGlobalRankPaginatedPayload(
    val page: Int? = 0,
    val size: Int? = 6
)

@Serializable
data class GetFriendsRankPaginatedPayload(
    val page: Int? = 0,
    val size: Int? = 6
)