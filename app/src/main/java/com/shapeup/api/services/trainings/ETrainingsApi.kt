package com.shapeup.api.services.trainings

import com.shapeup.api.utils.helpers.AppClient
import com.shapeup.api.utils.helpers.SharedData

interface ETrainingsApi {
    suspend fun searchTrainings(payload: SearchTrainingsPayload): SearchTrainingsStatement

    suspend fun searchTrainingsByCategory(
        payload: SearchTrainingsByCategoryPayload
    ): SearchTrainingsByCategoryStatement

    suspend fun getUserTrainings(): GetUserTrainingsStatement

    suspend fun addTraining(
        payload: AddTrainingsPayload
    ): GenericTrainingUpdateStatement

    suspend fun deleteTraining(
        payload: DeleteTrainingPayload
    ): GenericTrainingUpdateStatement

    suspend fun finishTraining(
        payload: FinishTrainingPayload
    ): GenericTrainingUpdateStatement

    suspend fun getTrainings(): GetTrainingsStatement

    suspend fun updateTraining(
        payload: UpdateTrainingPayload
    ): UpdateTrainingStatement
    companion object {
        fun create(sharedData: SharedData): ETrainingsApi {
            return TrainingsApi(
                client = AppClient,
                sharedData = sharedData
            )
        }
    }
}