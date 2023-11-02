package com.shapeup.api.services.users

import com.shapeup.api.utils.helpers.AppClient
import com.shapeup.api.utils.helpers.SharedData

interface EUsersApi {
    suspend fun searchUsers(payload: SearchUsersPayload): SearchUsersStatement
    suspend fun getRank(payload: GetRankPayload): GetRankStatement

    companion object {
        fun create(sharedData: SharedData): EUsersApi {
            return UsersApi(
                client = AppClient,
                sharedData = sharedData
            )
        }
    }
}