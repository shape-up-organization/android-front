package com.shapeup.api.services.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    @SerialName("jwt-token")
    val jwtToken: String
)