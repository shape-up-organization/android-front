package com.shapeup.api.services.users

import com.shapeup.api.utils.constants.BASE_URL
import com.shapeup.api.utils.constants.SharedDataValues
import com.shapeup.api.utils.helpers.SharedData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class UsersApi(
    private val client: HttpClient,
    private val sharedData: SharedData,
) : EUsersApi {
    override suspend fun deleteByEmail(): DeleteByEmailStatement {
        var response: HttpResponse? = null

        try {
            response = client.delete("$BASE_URL/users") {
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
                return DeleteByEmailStatement(
                    status = response.status
                )
            }

            else -> {
                DeleteByEmailStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun findByUsername(payload: GetUserPayload): GetUserStatement {
        var response: HttpResponse? = null

        try {
            response = client.get("$BASE_URL/users/find-username/${payload.username}") {
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
                return GetUserStatement(
                    data = response.body<UserSearch>(),
                    status = response.status
                )
            }

            else -> {
                GetUserStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun getRank(payload: GetRankPayload): GetRankStatement {
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

    override suspend fun getUserXp(): GetUserXpStatement {
        var response: HttpResponse? = null

        try {
            response = client.get("$BASE_URL/users/user-xp") {
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
                return GetUserXpStatement(
                    data = response.body<Int>(),
                    status = response.status
                )
            }

            else -> {
                GetUserXpStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun searchUsersByUsername(
        payload: SearchByUsernamePayload
    ): SearchUsersStatement {
        var response: HttpResponse? = null

        try {
            response = client.get("$BASE_URL/users/search-username/${payload.username}") {
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

    override suspend fun searchUsersByFullName(
        payload: SearchByFullNamePayload
    ): SearchUsersStatement {
        var response: HttpResponse? = null

        try {
            response = client.get("$BASE_URL/users/search-fullname") {
                contentType(ContentType.Application.Json)
                header(
                    HttpHeaders.Authorization,
                    "Bearer ${sharedData.get(SharedDataValues.JwtToken.value)}"
                )
                parameter("fullName", payload.fullName)
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

    override suspend fun updateUserField(payload: UserFieldPayload): UserFieldStatement {
        var response: HttpResponse? = null

        try {
            response = client.put("$BASE_URL/users") {
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

        return when (response?.status) {
            HttpStatusCode.OK -> {
                return UserFieldStatement(
                    data = response.body<UserFieldUpdate>(),
                    status = response.status
                )
            }

            else -> {
                UserFieldStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun updateProfilePicture(
        payload: UpdateProfilePicturePayload
    ): UpdateProfilePictureStatement {
        var response: HttpResponse? = null

        try {
            response = client.post("$BASE_URL/profiles/picture") {
                contentType(ContentType.Application.Json)
                header(
                    HttpHeaders.Authorization,
                    "Bearer ${sharedData.get(SharedDataValues.JwtToken.value)}"
                )
                setBody(MultiPartFormDataContent(
                    formData {
                        payload.file.map { file ->
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
        } catch (_: Exception) {
            println("ERROR: Timeout or Service Unavailable")
        }

        return when (response?.status) {
            HttpStatusCode.OK -> {
                UpdateProfilePictureStatement(
                    data = response.body<UpdateProfilePictureResponse>(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }

            else -> {
                UpdateProfilePictureStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun getAddressByZipCode(
        payload: GetAddressByZipCodePayload
    ): GetAddressByZipCodeStatement {
        var response: HttpResponse? = null

        try {
            response = client.get("https://viacep.com.br/ws/${payload.zipCode}/json") {
                contentType(ContentType.Application.Json)
            }
        } catch (_: Exception) {
            println("ERROR: Timeout or Service Unavailable")
        }

        return when (response?.status) {
            HttpStatusCode.OK -> {
                return GetAddressByZipCodeStatement(
                    data = response.body<GetAddressByZipCodeResponse>(),
                    status = response.status
                )
            }

            else -> {
                GetAddressByZipCodeStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }
}