package com.shapeup.ui.viewModels.logged

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.shapeup.api.services.posts.getCommentsByPostIdMock
import com.shapeup.api.services.posts.getPostsMock
import com.shapeup.ui.utils.helpers.Navigator

class PostsViewModel : ViewModel() {
    lateinit var navigator: Navigator

    val posts = mutableStateOf<List<Post>>(emptyList())
    val specificPosts = mutableStateOf<List<Post>>(emptyList())

    private fun getCommentsByPostId(postId: String): List<Comment>? {
        // TODO: implement getCommentsByPostId from service
        return getCommentsByPostIdMock
    }

    private fun getPosts(): List<Post> {
        // TODO: implement getPosts from service
        posts.value = getPostsMock
        return posts.value
    }

    private fun getPostById(postId: String): Post? {
        // TODO: implement getPostById from service
        return getPostsMock.find { post -> post.id == postId }
    }

    private fun getUserPosts(username: String): List<Post> {
        // TODO: implement getPostsByUsername from service
        specificPosts.value = getPostsMock.filter { post -> post.username == username }
        return specificPosts.value
    }

    private fun createPost(postData: PostCreation) {
        if (postData.photoUrls.isNullOrEmpty()) {
            if (postData.description.isNullOrBlank()) return

            // TODO: implement createPostWithoutPhotos from service
            println("description: ${postData.description}")
            return
        }

        // TODO: implement createPost from service
        println("description: ${postData.description}")
        println("photos:\n")
        postData.photoUrls.forEachIndexed { index, uri ->
            println("\tphoto $index: $uri")
        }
    }

    private fun toggleLike(postId: String) {
        // TODO: implement toggleLikePost from service
        println("toggle like post: $postId")
    }

    private fun sendComment(postId: String, commentMessage: String) {
        // TODO: implement sendComment from service
        println("postId $postId")
        println("commentMessage $commentMessage")
    }

    val handlers = PostsHandlers(
        getCommentsByPostId = ::getCommentsByPostId,
        getPosts = ::getPosts,
        getPostById = ::getPostById,
        getUserPosts = ::getUserPosts,
        createPost = ::createPost,
        toggleLike = ::toggleLike,
        sendComment = ::sendComment
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
    val getCommentsByPostId: (postId: String) -> List<Comment>?,
    val getPosts: () -> List<Post>?,
    val getPostById: (postId: String) -> Post?,
    val getUserPosts: (username: String) -> List<Post>?,
    val createPost: (postData: PostCreation) -> Unit,
    val toggleLike: (postId: String) -> Unit,
    val sendComment: (postId: String, commentMessage: String) -> Unit
)

val postsHandlersMock = PostsHandlers(
    getCommentsByPostId = { getCommentsByPostIdMock },
    getPosts = { getPostsMock },
    getPostById = { getPostsMock[0] },
    getUserPosts = { getPostsMock },
    createPost = { },
    toggleLike = { },
    sendComment = { _, _ -> }
)

data class Post(
    val countComments: Int,
    val countLike: Int,
    val createdAt: String,
    val description: String? = null,
    val id: String,
    val liked: Boolean = false,
    val photoUrls: List<String>,
    val username: String
)

data class PostCreation(
    val description: String? = null,
    val photoUrls: List<Uri>? = emptyList()
)

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
