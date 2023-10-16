package com.shapeup.api.services.users

import kotlinx.serialization.Serializable

@Serializable
data class SignInPayload(
    val email: String,
    val password: String
)