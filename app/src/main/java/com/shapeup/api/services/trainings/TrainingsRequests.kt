package com.shapeup.api.services.trainings

import com.shapeup.ui.viewModels.logged.ETrainingPeriod
import kotlinx.serialization.Serializable
import java.time.DayOfWeek

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
    val dayOfWeek: DayOfWeek,
    val period: ETrainingPeriod,
    val trainingId: String
)

@Serializable
data class DeleteTrainingPayload(
    val dayOfWeek: DayOfWeek,
    val period: ETrainingPeriod,
    val trainingId: String
)

@Serializable
data class FinishTrainingPayload(
    val dayOfWeek: DayOfWeek,
    val period: ETrainingPeriod,
    val trainingId: String
)

@Serializable
data class UpdateTrainingPayload(
    val dayOfWeek: String,
    val period: String,
    val trainingId: String
)