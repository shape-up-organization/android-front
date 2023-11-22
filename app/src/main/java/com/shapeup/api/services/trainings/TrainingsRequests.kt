package com.shapeup.api.services.trainings

data class SearchTrainingsPayload(
    val name: String
)

data class SearchTrainingsByCategoryPayload(
    val category: String
)

data class AddTrainingsPayload(
    val dayOfWeek: String,
    val period: String,
    val trainingId: String
)

data class DeleteTrainingPayload(
    val dayOfWeek: String,
    val period: String,
    val trainingId: String
)

data class FinishTrainingPayload(
    val dayOfWeek: String,
    val period: String,
    val trainingId: String
)

data class UpdateTrainingPayload(
    val dayOfWeek: String,
    val period: String,
    val trainingId: String
)