package com.shapeup.api.services.posts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostPayload(
    @SerialName("jwt-token")
    val jwtToken: String?,
    val page : Int,
    val size : Int
)