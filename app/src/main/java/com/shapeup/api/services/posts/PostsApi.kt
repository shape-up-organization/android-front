package com.shapeup.api.services.posts

import com.shapeup.api.utils.constants.BASE_URL
import com.shapeup.api.utils.constants.SharedDataValues
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.viewModels.logged.Comment
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class PostsApi(
    private val client: HttpClient,
    private val sharedData: SharedData,
) : EPostsApi {
    override suspend fun getPostsPaginated(
        payload: GetPostsPaginatedPayload
    ): GetPostsPaginatedStatement {
        var response: HttpResponse? = null

        try {
            response = client.get("$BASE_URL/posts") {
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
                return GetPostsPaginatedStatement(
                    data = response.body<List<Post>>(),
                    status = response.status
                )
            }

            HttpStatusCode.NoContent -> {
                return GetPostsPaginatedStatement(
                    content = response.bodyAsText(),
                    status = response.status
                )
            }

            else -> {
                GetPostsPaginatedStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun getPostById(
        payload: GetPostByIdPayload
    ): GetPostByIdStatement {
        var response: HttpResponse? = null

        try {
            response = client.get("$BASE_URL/posts/${payload.postId}") {
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
                return GetPostByIdStatement(
                    data = response.body<Post>(),
                    status = response.status
                )
            }

            else -> {
                GetPostByIdStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun getCommentsByPostId(
        payload: GetCommentsByPostsIdPaginatedPayload
    ): GetCommentsByPostsIdPaginatedStatement {
        var response: HttpResponse? = null

        try {
            response = client.get("$BASE_URL/comments/post/${payload.postId}") {
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
                return GetCommentsByPostsIdPaginatedStatement(
                    data = response.body<List<Comment>>(),
                    status = response.status
                )
            }

            HttpStatusCode.NoContent -> {
                return GetCommentsByPostsIdPaginatedStatement(
                    content = response.bodyAsText(),
                    status = response.status
                )
            }

            else -> {
                GetCommentsByPostsIdPaginatedStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun sendComment(
        payload: SendCommentPayload
    ): SendCommentStatement {
        var response: HttpResponse? = null

        try {
            response = client.post("$BASE_URL/comments") {
                contentType(ContentType.Application.Json)
                header(
                    HttpHeaders.Authorization,
                    "Bearer ${sharedData.get(SharedDataValues.JwtToken.value)}"
                )
                setBody(payload)
            }
        } catch (_: Exception) {
            println("ERROR: Timeout or Service Unavailable")
        }

        return SendCommentStatement(
            content = response?.bodyAsText(),
            status = response?.status ?: HttpStatusCode.ServiceUnavailable
        )
    }

    override suspend fun toggleLike(
        payload: ToggleLikePayload
    ): ToggleLikeStatement {
        var response: HttpResponse? = null

        try {
            response = client.post("$BASE_URL/posts/${payload.postId}/like") {
                contentType(ContentType.Application.Json)
                header(
                    HttpHeaders.Authorization,
                    "Bearer ${sharedData.get(SharedDataValues.JwtToken.value)}"
                )
            }
        } catch (_: Exception) {
            println("ERROR: Timeout or Service Unavailable")
        }

        return ToggleLikeStatement(
            content = response?.bodyAsText(),
            status = response?.status ?: HttpStatusCode.ServiceUnavailable
        )
    }

    override suspend fun deletePost(
        payload: DeletePostPayload
    ): DeletePostStatement {
        var response: HttpResponse? = null

        try {
            response = client.delete("$BASE_URL/posts/${payload.postId}") {
                contentType(ContentType.Application.Json)
                header(
                    HttpHeaders.Authorization,
                    "Bearer ${sharedData.get(SharedDataValues.JwtToken.value)}"
                )
            }
        } catch (_: Exception) {
            println("ERROR: Timeout or Service Unavailable")
        }

        return DeletePostStatement(
            content = response?.bodyAsText(),
            status = response?.status ?: HttpStatusCode.ServiceUnavailable
        )
    }

    override suspend fun createPost(
        payload: CreatePostPayload
    ): CreatePostStatement {
        var response: HttpResponse? = null

        try {
            response = client.post("$BASE_URL/posts") {
                contentType(ContentType.Application.Json)
                header(
                    HttpHeaders.Authorization,
                    "Bearer ${sharedData.get(SharedDataValues.JwtToken.value)}"
                )
                setBody(MultiPartFormDataContent(
                    formData {
                        if (!payload.description.isNullOrBlank()) append(
                            "description",
                            payload.description
                        )
                        payload.files.map { file ->
                            if (file != null) {
                                append(
                                    "file",
                                    file,
                                    Headers.build {
                                        append(HttpHeaders.ContentType, "image/*")
                                        append(HttpHeaders.ContentDisposition, "filename=\"$file.png\"")
                                    })
                            }
                        }
                    }
                ))
            }
        } catch (e: Exception) {
            println("ERROR: Timeout or Service Unavailable")
            println("ERRORRRRRRR: $e")
        }

        return CreatePostStatement(
            content = response?.bodyAsText(),
            status = response?.status ?: HttpStatusCode.ServiceUnavailable
        )
    }

    override suspend fun createPostWithoutFile(
        payload: CreatePostWithoutFilePayload
    ): CreatePostStatement {
        var response: HttpResponse? = null

        try {
            response = client.post("$BASE_URL/posts/without-photo") {
                contentType(ContentType.Application.Json)
                header(
                    HttpHeaders.Authorization,
                    "Bearer ${sharedData.get(SharedDataValues.JwtToken.value)}"
                )
                setBody(payload)
            }
        } catch (_: Exception) {
            println("ERROR: Timeout or Service Unavailable")
        }

        return CreatePostStatement(
            content = response?.bodyAsText(),
            status = response?.status ?: HttpStatusCode.ServiceUnavailable
        )
    }

}