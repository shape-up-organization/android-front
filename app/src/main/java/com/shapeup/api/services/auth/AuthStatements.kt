package com.shapeup.api.services.auth

import io.ktor.http.HttpStatusCode

data class SignInStatement(
    val data: SignInResponse? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class SignUpStatement(
    val content: String? = null,
    val status: HttpStatusCode
)

data class SendEmailCodeStatement(
    val content: String? = null,
    val status: HttpStatusCode
)

data class ConfirmEmailStatement(
    val content: String? = null,
    val status: HttpStatusCode
)