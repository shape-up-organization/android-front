package com.shapeup.api.services.trainings

import com.shapeup.ui.viewModels.logged.Training
import com.shapeup.ui.viewModels.logged.UserTrainingDay
import io.ktor.http.HttpStatusCode

data class SearchTrainingsStatement(
    val data: List<TrainingResponse>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class SearchTrainingsByCategoryStatement(
    val data: List<TrainingResponse>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class GetUserTrainingsStatement(
    val data: List<UserTrainingDay>? = null,
    val content: String? = null,
    val status: HttpStatusCode
)

data class GenericTrainingUpdateStatement(
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