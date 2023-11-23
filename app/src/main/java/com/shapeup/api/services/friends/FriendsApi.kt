package com.shapeup.api.services.friends

import com.shapeup.api.utils.constants.BASE_URL
import com.shapeup.api.utils.constants.SharedDataValues
import com.shapeup.api.utils.helpers.SharedData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class FriendsApi(
    private val client: HttpClient,
    private val sharedData: SharedData,
) : EFriendsApi {
    override suspend fun sendFriendshipRequest(
        payload: RequestFriendshipPayload
    ): RequestFriendshipStatement {
        var response: HttpResponse? = null

        try {
            response = client.post("$BASE_URL/friends/sent-friendship-request/${payload.newFriendUsername}") {
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
                return RequestFriendshipStatement(
                    data = response.body<RequestFriendship>(),
                    status = response.status
                )
            }

            else -> {
                RequestFriendshipStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun acceptFriendshipRequest(
        payload: AcceptFriendshipPayload
    ): AcceptFriendshipStatement {
        var response: HttpResponse? = null

        try {
            response = client.post("$BASE_URL/friends/accept-friendship-request/${payload.friendUsername}") {
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
                return AcceptFriendshipStatement(
                    data = response.body<AcceptFriendship>(),
                    status = response.status
                )
            }

            else -> {
                AcceptFriendshipStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun getAllFriendship(
    ): GetAllFriendshipStatement {
        var response: HttpResponse? = null

        try {
            response = client.get("$BASE_URL/friends/get-all-friendship") {
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
                return GetAllFriendshipStatement(
                    data = response.body<List<Friendship>>(),
                    status = response.status
                )
            }

            else -> {
                GetAllFriendshipStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun deleteFriendshipRequest(
        payload: DeleteFriendshipPayload
    ): DeleteFriendshipStatement {
        var response: HttpResponse? = null

        try {
            response = client.delete("$BASE_URL/friends/delete-friendship-request/${payload.friendUsername}") {
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
                return DeleteFriendshipStatement(
                    status = response.status
                )
            }

            else -> {
                DeleteFriendshipStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun deleteFriend(
        payload: DeleteFriendPayload
    ): DeleteFriendStatement {
        var response: HttpResponse? = null

        try {
            response = client.delete("$BASE_URL/delete-friend/${payload.friendUsername}") {
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
                return DeleteFriendStatement(
                    status = response.status
                )
            }

            else -> {
                DeleteFriendStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }
}