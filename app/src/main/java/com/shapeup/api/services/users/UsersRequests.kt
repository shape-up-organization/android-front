package com.shapeup.api.services.users

import java.time.LocalDate

data class UserFieldPayload(
    val email: String,
    val name: String,
    val lastName: String,
    val cellPhone: String,
    val birth: LocalDate,
    val biography: String,
    val username: String,
    val password: String,
)

data class SearchByUsernamePayload(
    val username: String
)

data class SearchByFullNamePayload(
    val fullName: String
)