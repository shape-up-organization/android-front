package com.shapeup.api.services.posts

import io.ktor.client.HttpClient

class PostsApi(
    private val client: HttpClient
) : EPostsApi {

}