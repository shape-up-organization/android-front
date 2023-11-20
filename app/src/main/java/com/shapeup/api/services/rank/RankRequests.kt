package com.shapeup.api.services.rank

data class GetGlobalRankPaginatedPayload(
    val page: Int? = 0,
    val size: Int? = 6
)

data class GetFriendsRankPaginatedPayload(
    val page: Int? = 0,
    val size: Int? = 6
)