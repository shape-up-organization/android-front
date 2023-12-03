package com.shapeup.api.services.posts

import com.shapeup.api.utils.helpers.AppClient
import com.shapeup.api.utils.helpers.SharedData

interface EPostsApi {
    suspend fun getPostsPaginated(payload: GetPostsPaginatedPayload): GetPostsPaginatedStatement
    suspend fun getPostsByUsername(payload: GetPostsByUsernamePayload): GetPostsPaginatedStatement

    suspend fun getPostById(payload: GetPostByIdPayload): GetPostByIdStatement
    suspend fun getCommentsByPostId(
        payload: GetCommentsByPostsIdPaginatedPayload
    ): GetCommentsByPostsIdPaginatedStatement

    suspend fun sendComment(
        payload: SendCommentPayload
    ): SendCommentStatement

    suspend fun toggleLike(
        payload: ToggleLikePayload
    ): ToggleLikeStatement

    suspend fun deletePost(
        payload: DeletePostPayload
    ): DeletePostStatement

    suspend fun createPost(
        payload: CreatePostPayload
    ): CreatePostStatement

    suspend fun createPostWithoutFile(
        payload: CreatePostWithoutFilePayload
    ): CreatePostStatement

    companion object {
        fun create(sharedData: SharedData): EPostsApi {
            return PostsApi(
                client = AppClient,
                sharedData = sharedData
            )
        }
    }
}