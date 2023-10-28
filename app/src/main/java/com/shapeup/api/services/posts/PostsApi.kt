package com.shapeup.api.services.posts

import com.shapeup.api.utils.constants.BASE_URL
import com.shapeup.api.utils.helpers.SharedData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class PostsApi(
    private val client: HttpClient
) : EPostsApi {

    override suspend fun posts(payload: PostPayload): PostStatement {
        var response: HttpResponse? = null

        try {
            response = client.get("$BASE_URL/posts") {
                contentType(ContentType.Application.Json)
                setBody(payload)
            }
        } catch (_: Exception) {
            println("ERROR: Timeout or Service Unavailable")
        }

        return when (response?.status) {
            HttpStatusCode.OK -> {
                val data = response.body<List<PostResponse>>()

                return PostStatement(
                    data = data,
                    status = response.status
                )
            }

            else -> {
                PostStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }
}