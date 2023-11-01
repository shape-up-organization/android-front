package com.shapeup.api.services.auth

import com.shapeup.api.utils.helpers.AppClient
import com.shapeup.api.utils.helpers.SharedData

interface EAuthApi {
    suspend fun signIn(payload: SignInPayload): SignInStatement
    suspend fun signUp(payload: SignUpPayload): SignUpStatement

    suspend fun sendEmailCode(payload: SendEmailCodePayload): SendEmailCodeStatement

    suspend fun confirmEmail(payload: ConfirmEmailPayload): ConfirmEmailStatement

    companion object {
        fun create(sharedData: SharedData): EAuthApi {
            return AuthApi(
                client = AppClient,
                sharedData = sharedData
            )
        }
    }
}