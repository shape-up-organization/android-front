package com.shapeup.api.services.users

import com.shapeup.api.utils.helpers.AppClient
import com.shapeup.api.utils.helpers.SharedData

interface EUsersApi {
    suspend fun signIn(payload: SignInPayload): SignInStatement

    companion object {
        fun create(sharedData: SharedData): EUsersApi {
            return UsersApi(
                client = AppClient,
                sharedData = sharedData
            )
        }
    }
}