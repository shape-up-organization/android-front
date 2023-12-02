package com.shapeup.api.services.users

import com.shapeup.ui.viewModels.logged.FriendshipStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSearch(
    val biography: String? = null,
    val firstName: String,
    val lastName: String,
    val profilePicture: String? = null,
    val username: String,
    val xp: Int,
    val friendshipStatus: FriendshipStatus? = null
)

@Serializable
data class UserRank(
    val firstName: String,
    val lastName: String,
    val profilePicture: String? = null,
    val username: String,
    val xp: Int,
)

@Serializable
data class UserFieldUpdate(
    val token: String,
    val updatedAt: String
)

@Serializable
data class SearchByUsername(
    val firstName: String,
    val lastName: String,
    val username: String,
    val profilePicture: String,
    val biography: String,
    val xp: Long,
    val friendshipStatus: FriendshipSearchStatus,
)

@Serializable
data class FriendshipSearchStatus(
    val haveFriendRequest: Boolean,
    val isFriend: Boolean,
    val userSenderFriendshipRequest: String
)

@Serializable
data class UpdateProfilePictureResponse(
    val jwt: String,
    val pictureProfile: String
)

@Serializable
data class GetAddressByZipCodeResponse(
    @SerialName("logradouro")
    val street: String,
    @SerialName("bairro")
    val district: String,
    @SerialName("localidade")
    val city: String,
    @SerialName("uf")
    val state: String,
)