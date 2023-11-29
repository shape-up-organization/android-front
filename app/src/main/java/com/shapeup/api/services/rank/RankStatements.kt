package com.shapeup.api.services.rank

import com.shapeup.ui.viewModels.logged.User
import io.ktor.http.HttpStatusCode

data class GetGlobalRankPaginatedStatement(
    val data: List<User>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class GetFriendsRankPaginatedStatement(
    val data: List<User>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)