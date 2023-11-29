package com.shapeup.api.services.users

import com.shapeup.api.utils.helpers.AppClient
import com.shapeup.api.utils.helpers.SharedData

interface EUsersApi {
    suspend fun deleteByEmail(): DeleteByEmailStatement
    suspend fun getRank(payload: GetRankPayload): GetRankStatement
    suspend fun getUser(payload: GetUserPayload): GetUserStatement
    suspend fun getUserXp(): GetUserXpStatement
    suspend fun searchByUsername(payload: SearchByUsernamePayload): SearchByUsernameStatement
    suspend fun searchUsersByUsername(payload: SearchByUsernamePayload): SearchUsersStatement
    suspend fun searchUsersByFullName(payload: SearchByFullNamePayload): SearchUsersStatement
    suspend fun updateUserField(payload: UserFieldPayload): UserFieldStatement

    companion object {
        fun create(sharedData: SharedData): EUsersApi {
            return UsersApi(
                client = AppClient,
                sharedData = sharedData
            )
        }
    }
}