package com.shapeup.api.services.auth

import com.shapeup.api.utils.constants.BASE_URL
import com.shapeup.api.utils.constants.SharedDataValues
import com.shapeup.api.utils.helpers.SharedData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class AuthApi(
    private val client: HttpClient,
    private val sharedData: SharedData,
) : EAuthApi {
    override suspend fun signIn(payload: SignInPayload): SignInStatement {
        var response: HttpResponse? = null

        try {
            response = client.post("$BASE_URL/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(payload)
            }
        } catch (_: Exception) {
            println("ERROR: Timeout or Service Unavailable")
        }


        return when (response?.status) {
            HttpStatusCode.OK -> {
                val data = response.body<SignInResponse>()
                val email = sharedData.get(SharedDataValues.Email.value)

                sharedData.save(SharedDataValues.JwtToken.value, data.jwtToken)
                if (email.isNullOrBlank()) {
                    sharedData.save(SharedDataValues.Email.value, payload.email)
                    sharedData.save(SharedDataValues.Password.value, payload.password)
                }

                return SignInStatement(
                    data = data,
                    status = response.status
                )
            }

            else -> {
                SignInStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun signUp(payload: SignUpPayload): SignUpStatement {
        var response: HttpResponse? = null

        try {
            response = client.post("$BASE_URL/auth/register") {
                contentType(ContentType.Application.Json)
                setBody(payload)
            }
        } catch (_: Exception) {
            println("ERROR: Timeout or Service Unavailable")
        }

        return when (response?.status) {
            HttpStatusCode.OK -> {
                return SignUpStatement(
                    status = response.status
                )
            }

            else -> {
                SignUpStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun sendEmailCode(payload: SendEmailCodePayload): SendEmailCodeStatement {
        var response: HttpResponse? = null

        try {
            response = client.post(
                "$BASE_URL/verification/send-email-code/${payload.email}"
            )
        } catch (_: Exception) {
            println("ERROR: Timeout or Service Unavailable")
        }

        println(response)


        return when (response?.status) {
            HttpStatusCode.OK -> {
                return SendEmailCodeStatement(
                    status = response.status
                )
            }

            else -> {
                SendEmailCodeStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun confirmEmail(payload: ConfirmEmailPayload): ConfirmEmailStatement {
        var response: HttpResponse? = null

        try {
            response = client.post("$BASE_URL/verification/confirm-email") {
                contentType(ContentType.Application.Json)
                setBody(payload)
            }
        } catch (_: Exception) {
            println("ERROR: Timeout or Service Unavailable")
        }


        return when (response?.status) {
            HttpStatusCode.OK -> {
                return ConfirmEmailStatement(
                    status = response.status
                )
            }

            else -> {
                ConfirmEmailStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }
}