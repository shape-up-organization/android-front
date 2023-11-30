package com.shapeup.api.services.friends

import com.shapeup.api.utils.helpers.AppClient
import com.shapeup.api.utils.helpers.SharedData

interface EFriendsApi {
    suspend fun sendFriendshipRequest(
        payload: RequestFriendshipPayload
    ): RequestFriendshipStatement

    suspend fun acceptFriendshipRequest(
        payload: AcceptFriendshipRequestPayload
    ): AcceptFriendshipRequestStatement

    suspend fun getAllFriendship(): GetAllFriendshipStatement

    suspend fun deleteFriendshipRequest(
        payload: DeleteFriendshipRequestPayload
    ): DeleteFriendshipRequestStatement

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