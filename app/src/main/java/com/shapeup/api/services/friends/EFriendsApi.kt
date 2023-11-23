package com.shapeup.api.services.friends

import com.shapeup.api.utils.helpers.AppClient
import com.shapeup.api.utils.helpers.SharedData

interface EFriendsApi {
    suspend fun sendFriendshipRequest(payload: RequestFriendshipPayload
    ): RequestFriendshipStatement

    suspend fun acceptFriendshipRequest(
        payload: AcceptFriendshipPayload
    ): AcceptFriendshipStatement

    suspend fun getAllFriendship(
    ): GetAllFriendshipStatement

    suspend fun deleteFriendshipRequest(
        payload: DeleteFriendshipPayload
    ): DeleteFriendshipStatement

    suspend fun deleteFriend(
        payload: DeleteFriendPayload
    ): DeleteFriendStatement

    companion object {
        fun create(sharedData: SharedData): EFriendsApi {
            return FriendsApi(
                client = AppClient,
                sharedData = sharedData
            )
        }
    }
}