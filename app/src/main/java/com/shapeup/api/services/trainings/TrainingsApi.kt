package com.shapeup.api.services.trainings

import com.shapeup.api.utils.constants.BASE_URL
import com.shapeup.api.utils.constants.SharedDataValues
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.viewModels.logged.Training
import com.shapeup.ui.viewModels.logged.UserTrainingDay
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class TrainingsApi(
    val client: HttpClient,
    private val sharedData: SharedData
) : ETrainingsApi {
    override suspend fun searchTrainings(
        payload: SearchTrainingsPayload
    ): SearchTrainingsStatement {
        var response: HttpResponse? = null

        try {
            response = client.get("$BASE_URL/quests/search-training") {
                contentType(ContentType.Application.Json)
                header(
                    HttpHeaders.Authorization,
                    "Bearer ${sharedData.get(SharedDataValues.JwtToken.value)}"
                )
                parameter("name", payload.name)
            }
        } catch (_: Exception) {
            println("ERROR: Timeout or Service Unavailable")
        }

        return when (response?.status) {
            HttpStatusCode.OK -> {
                return SearchTrainingsStatement(
                    data = response.body<List<TrainingResponse>>(),
                    status = response.status
                )
            }

            else -> {
                SearchTrainingsStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun searchTrainingsByCategory(
        payload: SearchTrainingsByCategoryPayload
    ): SearchTrainingsByCategoryStatement {
        var response: HttpResponse? = null

        try {
            response = client.get("$BASE_URL/quests/category/${payload.category}") {
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
                return SearchTrainingsByCategoryStatement(
                    data = response.body<List<TrainingResponse>>(),
                    status = response.status
                )
            }

            else -> {
                SearchTrainingsByCategoryStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun getUserTrainings(): GetUserTrainingsStatement {
        var response: HttpResponse? = null

        try {
            response = client.get("$BASE_URL/quests/user/trainings") {
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
                return GetUserTrainingsStatement(
                    data = response.body<List<UserTrainingDay>>(),
                    status = response.status
                )
            }

            else -> {
                GetUserTrainingsStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun addTraining(payload: AddTrainingsPayload): GenericTrainingUpdateStatement {
        var response: HttpResponse? = null

        try {
            response = client.post("$BASE_URL/quests/user/add-training") {
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

        return GenericTrainingUpdateStatement(
            content = response?.bodyAsText(),
            status = response?.status ?: HttpStatusCode.ServiceUnavailable
        )
    }

    override suspend fun deleteTraining(payload: DeleteTrainingPayload): GenericTrainingUpdateStatement {
        var response: HttpResponse? = null

        try {
            response = client.delete("$BASE_URL/quests/user/remove-training") {
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

        return GenericTrainingUpdateStatement(
            content = response?.bodyAsText(),
            status = response?.status ?: HttpStatusCode.ServiceUnavailable
        )
    }

    override suspend fun finishTraining(payload: FinishTrainingPayload): GenericTrainingUpdateStatement {
        var response: HttpResponse? = null

        try {
            response = client.put("$BASE_URL/quests/user/finish-training") {
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

        return GenericTrainingUpdateStatement(
            content = response?.bodyAsText(),
            status = response?.status ?: HttpStatusCode.ServiceUnavailable
        )
    }

    override suspend fun getTrainings(): GetTrainingsStatement {
        var response: HttpResponse? = null

        try {
            response = client.get("$BASE_URL/quests/trainings") {
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
                return GetTrainingsStatement(
                    data = response.body<List<Training>>(),
                    status = response.status
                )
            }

            else -> {
                GetTrainingsStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }

    override suspend fun updateTraining(payload: UpdateTrainingPayload): UpdateTrainingStatement {
        var response: HttpResponse? = null

        try {
            response = client.put("$BASE_URL/quests/user/finish-training") {
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
                return UpdateTrainingStatement(
                    data = response.body<UpdateTraining>(),
                    status = response.status
                )
            }

            else -> {
                UpdateTrainingStatement(
                    content = response?.bodyAsText(),
                    status = response?.status ?: HttpStatusCode.ServiceUnavailable
                )
            }
        }
    }
}