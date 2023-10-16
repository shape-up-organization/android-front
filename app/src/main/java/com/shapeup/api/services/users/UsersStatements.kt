package com.shapeup.api.services.users

import io.ktor.http.HttpStatusCode

data class SignInStatement(
    val data: SignInResponse? = null,
    val status: HttpStatusCode
)