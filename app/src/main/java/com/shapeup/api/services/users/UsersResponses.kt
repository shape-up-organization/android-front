package com.shapeup.api.services.users

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    @SerialName("jwt-token")
    val jwtToken: String
)