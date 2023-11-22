package com.shapeup.api.services.rank

import com.shapeup.api.utils.helpers.AppClient
import com.shapeup.api.utils.helpers.SharedData

interface ERankApi {
    suspend fun getGlobalRankPaginated(payload: GetGlobalRankPaginatedPayload
    ): GetGlobalRankPaginatedStatement
    suspend fun getFriendsRankPaginated(payload: GetFriendsRankPaginatedPayload
    ): GetFriendsRankPaginatedStatement

    companion object {
        fun create(sharedData: SharedData): ERankApi {
            return RankApi(
                client = AppClient,
                sharedData = sharedData
            )
        }
    }
}