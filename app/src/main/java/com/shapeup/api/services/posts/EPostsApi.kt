package com.shapeup.api.services.posts

import com.shapeup.api.utils.helpers.AppClient

interface EPostsApi {
    suspend fun posts(payload: PostPayload): PostStatement
    companion object {
        fun create(): EPostsApi {
            return PostsApi(
                client = AppClient
            )
        }
    }
}