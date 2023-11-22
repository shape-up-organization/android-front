package com.shapeup.api.services.trainings

import io.ktor.http.HttpStatusCode

data class SearchTrainingsStatement(
    val data: List<Training>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class SearchTrainingsByCategoryStatement(
    val data: List<Training>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class SearchTrainingsByUserIdStatement(
    val data: List<TrainingByUserId>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class AddTrainingsStatement(
    val data: AddTraining? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class DeleteTrainingStatement(
    val content: String? = null,
    val status: HttpStatusCode
)

data class FinishTrainingStatement(
    val data: FinishTraining? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class GetTrainingsStatement(
    val data: List<Training>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class UpdateTrainingStatement(
    val data: UpdateTraining? = null,
    val content: String? = null,
    val status: HttpStatusCode
)