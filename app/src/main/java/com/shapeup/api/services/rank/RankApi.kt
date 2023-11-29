package com.shapeup.api.services.rank

import com.shapeup.api.utils.constants.BASE_URL
import com.shapeup.api.utils.constants.SharedDataValues
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.viewModels.logged.User
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

class RankApi (
    val client: HttpClient,
    private val sharedData: SharedData,
    ) : ERankApi  {
    override suspend fun getGlobalRankPaginated(
        payload: GetGlobalRankPaginatedPayload
    ): GetGlobalRankPaginatedStatement {
        var response: HttpResponse? = null

        try {
            response = client.get("$BASE_URL/rank/global") {
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
                return GetGlobalRankPaginatedStatement(
                    data = response.body<List<User>>(),
                    status = response.status
                )
            }

            HttpStatusCode.NoContent -> {
                return GetGlobalRankPaginatedStatement(
                    content = response.bodyAsText(),
                    status = response.status
                )
            }

            else -> {
                GetGlobalRankPaginatedStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun getFriendsRankPaginated(
        payload: GetFriendsRankPaginatedPayload
    ): GetFriendsRankPaginatedStatement {
        var response: HttpResponse? = null

        try {
            response = client.get("$BASE_URL/rank/global") {
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
                return GetFriendsRankPaginatedStatement(
                    data = response.body<List<User>>(),
                    status = response.status
                )
            }

            HttpStatusCode.NoContent -> {
                return GetFriendsRankPaginatedStatement(
                    content = response.bodyAsText(),
                    status = response.status
                )
            }

            else -> {
                GetFriendsRankPaginatedStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }
}