package com.shapeup.api.services.users

import io.ktor.http.HttpStatusCode

data class DeleteByEmailStatement(
    val content: String? = null,
    val status: HttpStatusCode
)

data class GetRankStatement(
    val data: List<UserRank>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class GetUserStatement(
    val data: UserSearch? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class GetUserXpStatement(
    val data: Long? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class SearchUsersStatement(
    val data: List<UserSearch>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class UserFieldStatement(
    val data: UserFieldUpdate? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class UpdateProfileStatement(
    val pictureStatus: HttpStatusCode,
    val userDataStatus: HttpStatusCode
)

data class UpdateSettingsStatement(
    val content: String? = null,
    val status: HttpStatusCode
)

data class UpdateProfilePictureStatement(
    val data: UpdateProfilePictureResponse? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class GetAddressByZipCodeStatement(
    val data: GetAddressByZipCodeResponse? = null,
    val content: String? = null,
    val status: HttpStatusCode
)