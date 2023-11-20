package com.shapeup.ui.viewModels.logged

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.shapeup.api.services.posts.CreatePostStatement
import com.shapeup.api.services.posts.DeletePostStatement
import com.shapeup.api.services.posts.GetCommentsByPostsIdPaginatedStatement
import com.shapeup.api.services.posts.GetPostByIdStatement
import com.shapeup.api.services.posts.GetPostsPaginatedStatement
import com.shapeup.api.services.posts.Post
import com.shapeup.api.services.posts.SendCommentStatement
import com.shapeup.api.services.posts.ToggleLikeStatement
import com.shapeup.api.services.rank.ERankApi
import com.shapeup.api.services.rank.GetFriendsRankPaginatedPayload
import com.shapeup.api.services.rank.GetFriendsRankPaginatedStatement
import com.shapeup.api.services.rank.GetGlobalRankPaginatedPayload
import com.shapeup.api.services.rank.GetGlobalRankPaginatedStatement
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.utils.helpers.Navigator
import io.ktor.http.HttpStatusCode

class RankViewModel : ViewModel() {
    lateinit var navigator: Navigator
    lateinit var sharedData: SharedData

    val rank = mutableStateOf<List<User>>(emptyList())

    private suspend fun getGlobalRank(
        pagination: GetGlobalRankPaginatedPayload = GetGlobalRankPaginatedPayload(
            page = 0,
            size = 6
        )
    ): GetGlobalRankPaginatedStatement {
        val rankApi = ERankApi.create(sharedData)

        val response = rankApi.getGlobalRankPaginated(pagination)

        println(response)

        if (response.status === HttpStatusCode.OK && !response.data.isNullOrEmpty()) {
            rank.value = response.data
        }

        return response
    }

    private suspend fun getFriendsRank(
        pagination: GetFriendsRankPaginatedPayload = GetFriendsRankPaginatedPayload(
            page = 0,
            size = 6
        )
    ): GetFriendsRankPaginatedStatement {
        val rankApi = ERankApi.create(sharedData)

        val response = rankApi.getFriendsRankPaginated(pagination)

        println(response)

        if (response.status === HttpStatusCode.OK && !response.data.isNullOrEmpty()) {
            rank.value = response.data
        }

        return response
    }

    val handlers = GlobalRankHandlers(
        getGlobalRank = ::getGlobalRank
    )
}

data class GlobalRankHandlers(
    val getGlobalRank: suspend () -> GetGlobalRankPaginatedStatement
)