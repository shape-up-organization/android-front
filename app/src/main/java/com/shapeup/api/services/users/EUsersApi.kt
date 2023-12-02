package com.shapeup.api.services.users

import com.shapeup.api.utils.helpers.AppClient
import com.shapeup.api.utils.helpers.SharedData

interface EUsersApi {
    suspend fun deleteByEmail(): DeleteByEmailStatement
    suspend fun getRank(payload: GetRankPayload): GetRankStatement
    suspend fun getUserXp(): GetUserXpStatement
    suspend fun findByUsername(payload: GetUserPayload): GetUserStatement
    suspend fun searchUsersByUsername(payload: SearchByUsernamePayload): SearchUsersStatement
    suspend fun searchUsersByFullName(payload: SearchByFullNamePayload): SearchUsersStatement
    suspend fun updateUserField(payload: UserFieldPayload): UserFieldStatement
    suspend fun updateProfilePicture(
        payload: UpdateProfilePicturePayload
    ): UpdateProfilePictureStatement

    companion object {
        fun create(sharedData: SharedData): EUsersApi {
            return UsersApi(
                client = AppClient,
                sharedData = sharedData
            )
        }
    }
}