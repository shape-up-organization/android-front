package com.shapeup.api.services.users

import com.shapeup.api.utils.constants.BASE_URL
import com.shapeup.api.utils.constants.SharedDataValues
import com.shapeup.api.utils.helpers.SharedData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class UsersApi(
    private val client: HttpClient,
    private val sharedData: SharedData,
) : EUsersApi {
    override suspend fun searchUsers(payload: SearchUsersPayload): SearchUsersStatement {
        var response: HttpResponse? = null

        val mountedUrl = when (payload.nameOrUsername.startsWith("@")) {
            true -> "search-username/${payload.nameOrUsername.drop(1)}"
            else -> "search-fullname?fullName=${payload.nameOrUsername}"
        }

        try {
            response = client.get("$BASE_URL/users/$mountedUrl") {
                contentType(ContentType.Application.Json)
                header(
                    HttpHeaders.Authorization,
                    "Bearer ${sharedData.get(SharedDataValues.JwtToken.value)}"
                )
            }
        } catch (_: Exception) {
            println("ERROR: Timeout or Service Unavailable")
        }

        return when (response?.status) {
            HttpStatusCode.OK -> {
                return SearchUsersStatement(
                    data = response.body<List<UserSearch>>(),
                    status = response.status
                )
            }

            else -> {
                SearchUsersStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun getRank(
        payload: GetRankPayload
    ): GetRankStatement {
        var response: HttpResponse? = null

        try {
            response = client.get("$BASE_URL/rank/${payload.type.value}") {
                contentType(ContentType.Application.Json)
                header(
                    HttpHeaders.Authorization,
                    "Bearer ${sharedData.get(SharedDataValues.JwtToken.value)}"
                )
                parameter("page", payload.page)
                parameter("size", payload.size)
            }
        } catch (_: Exception) {
            println("ERROR: Timeout or Service Unavailable")
        }

        return when (response?.status) {
            HttpStatusCode.OK -> {
                return GetRankStatement(
                    data = response.body<List<UserRank>>(),
                    status = response.status
                )
            }

            else -> {
                GetRankStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }
}