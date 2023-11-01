package com.shapeup.api.services.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInPayload(
    val email: String,
    val password: String
)

@Serializable
data class SignUpPayload(
    val birth: String,
    val email: String,
    val name: String,
    @SerialName("last_name") val lastName: String,
    val password: String,
    @SerialName("cell_phone") val cellPhone: String,
    val username: String
)

@Serializable
data class SendEmailCodePayload(
    val email: String
)

@Serializable
data class ConfirmEmailPayload(
    val email: String,
    val code: String
)