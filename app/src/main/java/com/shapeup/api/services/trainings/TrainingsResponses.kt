package com.shapeup.api.services.trainings

import kotlinx.serialization.Serializable

@Serializable
data class Training(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val duration: Int,
    val classification: String,
    val xp: Int,
    val unlockXp: Int,
    val exercises: List<String>,
)

@Serializable
data class TrainingByUserId(
    val dayOfWeek: String,
    val training: List<TrainingPerPeriod>
)

@Serializable
data class TrainingPerPeriod(
    val period: String,
    val training: TrainingOfUser
)

@Serializable
data class TrainingOfUser(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val duration: Int,
    val classification: String,
    val xp: Int,
    val unlockXp: Int,
    val exercises: List<String>,
    val status: String
)

@Serializable
data class AddTraining(
    val id: String,
    val category: String,
    val trainingDay : TrainingDay,
    val xp: Int,
    val exercises: List<String>,
    val status: String
)

@Serializable
data class TrainingDay(
    val dayOfWeek: String,
    val period: String
)

@Serializable
data class FinishTraining(
    val id: String,
    val category: String,
    val trainingDay : TrainingDay,
    val xp: Int,
    val exercises: List<String>,
    val status: String
)

@Serializable
data class UpdateTraining(
    val id: String,
    val category: String,
    val trainingDay : TrainingDay,
    val xp: Int,
    val exercises: List<String>,
    val status: String
)