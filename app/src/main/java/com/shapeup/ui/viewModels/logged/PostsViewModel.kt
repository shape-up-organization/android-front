package com.shapeup.ui.viewModels.logged

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.shapeup.service.posts.getPostsMock
import com.shapeup.ui.utils.helpers.Navigator

class PostsViewModel : ViewModel() {
    lateinit var navigator: Navigator

    val posts = mutableStateOf<List<Post>>(emptyList())
    val specificPosts = mutableStateOf<List<Post>>(emptyList())

    private fun getPosts(): List<Post> {
        // TODO: implement getPosts from service
        posts.value = getPostsMock
        return posts.value
    }

    private fun getPostById(postId: String): Post? {
        // TODO: implement getPostById from service
        return getPostsMock.find { post -> post.id == postId }
    }

    private fun getUserPosts(userName: String): List<Post> {
        // TODO: implement getPostsByUsername from service
        specificPosts.value = getPostsMock.filter { post -> post.username == userName }
        return specificPosts.value
    }

    val handlers = PostsHandlers(
        getPosts = ::getPosts,
        getPostById = ::getPostById,
        getUserPosts = ::getUserPosts
    )
}

data class PostsData(
    val posts: MutableState<List<Post>>,
    val specificPosts: MutableState<List<Post>>
)

data class PostsHandlers(
    val getPosts: () -> List<Post>?,
    val getPostById: (String) -> Post?,
    val getUserPosts: (String) -> List<Post>?
)

data class Post(
    val countComments: Int,
    val countLike: Int,
    val createdAt: String,
    val description: String? = null,
    val id: String,
    val liked: Boolean? = false,
    val photoUrls: List<String>,
    val username: String
)
