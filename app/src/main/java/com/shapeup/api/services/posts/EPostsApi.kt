package com.shapeup.api.services.posts

import com.shapeup.api.utils.helpers.AppClient

interface EPostsApi {

    companion object {
        fun create(): EPostsApi {
            return PostsApi(
                client = AppClient
            )
        }
    }
}