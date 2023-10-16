package com.shapeup.api.services.users

import com.shapeup.api.utils.constants.BASE_URL
import com.shapeup.api.utils.helpers.SharedData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class UsersApi(
    private val client: HttpClient,
    private val sharedData: SharedData,
) : EUsersApi {
    override suspend fun signIn(payload: SignInPayload): SignInStatement {
        val response = client.post("$BASE_URL/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(payload)
        }

        return when (response.status) {
            HttpStatusCode.OK -> {
                val data = response.body<SignInResponse>()
                val token = sharedData.get("jwtToken")

                if (token.isNullOrBlank()) {
                    sharedData.save("jwtToken", data.jwtToken)
                }

                return SignInStatement(
                    data = data,
                    status = response.status
                )
            }

            else -> SignInStatement(status = response.status)
        }
    }
}