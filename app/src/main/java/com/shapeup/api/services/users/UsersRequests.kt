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
    val email: String? = null,
    val name: String? = null,
    val lastName: String? = null,
    val cellPhone: String? = null,
    val birth: String? = null,
    val biography: String? = null,
    val username: String? = null,
    val password: String? = null,
)

data class UpdateProfilePicturePayload(
    val file: List<ByteArray?>
)

data class GetAddressByZipCodePayload(
    val zipCode: String
)

enum class RankType(val value: String) {
    GLOBAL(value = "global"),
    FRIENDS(value = "friends")
}