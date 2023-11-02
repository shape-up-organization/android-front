package com.shapeup.ui.viewModels.logged

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.shapeup.api.services.posts.CreatePostPayload
import com.shapeup.api.services.posts.CreatePostStatement
import com.shapeup.api.services.posts.CreatePostWithoutFilePayload
import com.shapeup.api.services.posts.DeletePostPayload
import com.shapeup.api.services.posts.DeletePostStatement
import com.shapeup.api.services.posts.EPostsApi
import com.shapeup.api.services.posts.GetCommentsByPostsIdPaginatedPayload
import com.shapeup.api.services.posts.GetCommentsByPostsIdPaginatedStatement
import com.shapeup.api.services.posts.GetPostByIdPayload
import com.shapeup.api.services.posts.GetPostByIdStatement
import com.shapeup.api.services.posts.GetPostsPaginatedPayload
import com.shapeup.api.services.posts.GetPostsPaginatedStatement
import com.shapeup.api.services.posts.Post
import com.shapeup.api.services.posts.SendCommentPayload
import com.shapeup.api.services.posts.SendCommentStatement
import com.shapeup.api.services.posts.ToggleLikePayload
import com.shapeup.api.services.posts.ToggleLikeStatement
import com.shapeup.api.services.posts.getPostsMock
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.utils.helpers.Navigator
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

class PostsViewModel : ViewModel() {
    lateinit var navigator: Navigator
    lateinit var sharedData: SharedData

    val posts = mutableStateOf<List<Post>>(emptyList())
    val specificPosts = mutableStateOf<List<Post>>(emptyList())

    private suspend fun getCommentsByPostId(postId: String): GetCommentsByPostsIdPaginatedStatement {
        val postsApi = EPostsApi.create(sharedData)

        val response = postsApi.getCommentsByPostId(
            GetCommentsByPostsIdPaginatedPayload(
                postId = postId
            )
        )

        println(response)

        return response
    }

    private suspend fun getPosts(
        pagination: GetPostsPaginatedPayload = GetPostsPaginatedPayload(
            page = 0,
            size = 6
        )
    ): GetPostsPaginatedStatement {
        val postsApi = EPostsApi.create(sharedData)

        val response = postsApi.getPostsPaginated(pagination)

        println(response)

        when (response.status) {
            HttpStatusCode.OK -> posts.value = response.data!!
            HttpStatusCode.NoContent -> posts.value = emptyList()
        }

        return response
    }

    private suspend fun getPostById(postId: String): GetPostByIdStatement {
        val postsApi = EPostsApi.create(sharedData)

        val response = postsApi.getPostById(
            GetPostByIdPayload(
                postId = postId
            )
        )

        println(response)

        return response
    }

    private fun getUserPosts(username: String): List<Post> {
        // TODO: implement getPostsByUsername from service
        specificPosts.value = getPostsMock.filter { post -> post.username == username }
        return specificPosts.value
    }

    private suspend fun createPost(postData: PostCreation): CreatePostStatement {
        val postsApi = EPostsApi.create(sharedData)

        if (postData.photoUrls.isEmpty()) {
            if (postData.description.isNullOrBlank()) return CreatePostStatement(
                status = HttpStatusCode.BadRequest
            )

            val response = postsApi.createPostWithoutFile(
                CreatePostWithoutFilePayload(
                    description = postData.description
                )
            )

            println(response)

            return response
        }

        val response = postsApi.createPost(
            CreatePostPayload(
                description = postData.description,
                files = postData.photoUrls
            )
        )

        println(response)

        return response
    }

    private suspend fun toggleLike(postId: String): ToggleLikeStatement {
        val postsApi = EPostsApi.create(sharedData)

        val response = postsApi.toggleLike(
            ToggleLikePayload(
                postId = postId,
            )
        )

        println(response)

        return response
    }

    private suspend fun sendComment(postId: String, commentMessage: String): SendCommentStatement {
        val postsApi = EPostsApi.create(sharedData)

        val response = postsApi.sendComment(
            SendCommentPayload(
                postId = postId,
                commentMessage = commentMessage
            )
        )

        println(response)

        return response
    }

    private suspend fun deletePost(postId: String): DeletePostStatement {
        val postsApi = EPostsApi.create(sharedData)

        val response = postsApi.deletePost(
            DeletePostPayload(
                postId = postId
            )
        )

        println(response)

        return response
    }

    val handlers = PostsHandlers(
        getCommentsByPostId = ::getCommentsByPostId,
        getPosts = ::getPosts,
        getPostById = ::getPostById,
        getUserPosts = ::getUserPosts,
        createPost = ::createPost,
        toggleLike = ::toggleLike,
        sendComment = ::sendComment,
        deletePost = ::deletePost
    )
}

data class PostsData(
    val posts: MutableState<List<Post>>,
    val specificPosts: MutableState<List<Post>>
)

val postsDataMock = PostsData(
    posts = mutableStateOf(emptyList()),
    specificPosts = mutableStateOf(getPostsMock.filter { it.username == "g_johnston" })
)

data class PostsHandlers(
    val getCommentsByPostId: suspend (postId: String) -> GetCommentsByPostsIdPaginatedStatement,
    val getPosts: suspend () -> GetPostsPaginatedStatement,
    val getPostById: suspend (postId: String) -> GetPostByIdStatement,
    val getUserPosts: (username: String) -> List<Post>?,
    val createPost: suspend (postData: PostCreation) -> CreatePostStatement,
    val toggleLike: suspend (postId: String) -> ToggleLikeStatement,
    val sendComment: suspend (postId: String, commentMessage: String) -> SendCommentStatement,
    val deletePost: suspend (postId: String) -> DeletePostStatement
)

val postsHandlersMock = PostsHandlers(
    getCommentsByPostId = { _ ->
        GetCommentsByPostsIdPaginatedStatement(
            data = null,
            status = HttpStatusCode.OK
        )
    },
    getPosts = {
        GetPostsPaginatedStatement(
            status = HttpStatusCode.NoContent
        )
    },
    getPostById = {
        GetPostByIdStatement(
            data = getPostsMock[0],
            status = HttpStatusCode.OK
        )
    },
    getUserPosts = { getPostsMock },
    createPost = { CreatePostStatement(status = HttpStatusCode.Created) },
    toggleLike = {
        ToggleLikeStatement(
            status = HttpStatusCode.OK
        )
    },
    sendComment = { _, _ ->
        SendCommentStatement(
            status = HttpStatusCode.OK
        )
    },
    deletePost = {
        DeletePostStatement(
            status = HttpStatusCode.OK
        )
    }
)

data class PostCreation(
    val description: String? = null,
    val photoUrls: List<ByteArray?> = emptyList()
)

@Serializable
data class Comment(
    val commentId: String,
    val commentMessage: String,
    val createdAt: String,
    val firstName: String,
    val lastName: String,
    val profilePicture: String? = null,
    val username: String,
    val xp: Int
)
