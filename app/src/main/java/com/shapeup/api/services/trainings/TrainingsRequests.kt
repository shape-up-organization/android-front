package com.shapeup.api.services.trainings

import kotlinx.serialization.Serializable

@Serializable
data class SearchTrainingsPayload(
    val name: String
)

@Serializable
data class SearchTrainingsByCategoryPayload(
    val category: String
)

@Serializable
data class AddTrainingsPayload(
    val dayOfWeek: String,
    val period: String,
    val trainingId: String
)

@Serializable
data class DeleteTrainingPayload(
    val dayOfWeek: String,
    val period: String,
    val trainingId: String
)

@Serializable
data class FinishTrainingPayload(
    val dayOfWeek: String,
    val period: String,
    val trainingId: String
)

@Serializable
data class UpdateTrainingPayload(
    val dayOfWeek: String,
    val period: String,
    val trainingId: String
)