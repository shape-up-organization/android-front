package com.shapeup.api.services.users

import com.shapeup.ui.viewModels.logged.UserSearch
import io.ktor.http.HttpStatusCode

data class SearchUsersStatement(
    val data: List<UserSearch>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)