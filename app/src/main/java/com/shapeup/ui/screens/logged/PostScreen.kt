package com.shapeup.ui.screens.logged

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shapeup.api.services.users.getUserDataMock
import com.shapeup.ui.components.CardPost
import com.shapeup.ui.components.Loading
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.logged.JourneyHandlers
import com.shapeup.ui.viewModels.logged.PostView
import com.shapeup.ui.viewModels.logged.PostsHandlers
import com.shapeup.ui.viewModels.logged.journeyHandlersMock
import com.shapeup.ui.viewModels.logged.postsHandlersMock
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Preview
@Composable
fun PostScreenPreview() {
    ShapeUpTheme {
        PostScreen(
            navigator = Navigator(),
            postId = "1",
            postsHandlers = postsHandlersMock,
            journeyHandlers = journeyHandlersMock,
            username = getUserDataMock.username
        )
    }
}

@Composable
fun PostScreen(
    navigator: Navigator,
    postId: String,
    postsHandlers: PostsHandlers,
    journeyHandlers: JourneyHandlers,
    username: String
) {
    var loadingUserPosts by remember { mutableStateOf(true) }
    var postData by remember { mutableStateOf<PostView?>(null) }

    LaunchedEffect(key1 = true) {
        loadingUserPosts = true
        val response = postsHandlers.getPostById(postId)

        when (response.status) {
            HttpStatusCode.OK -> {
                val user = journeyHandlers.getUser(username)

                if (response.data != null && user != null) {
                    postData = PostView(
                        post = response.data,
                        user = user
                    )
                }
            }

            else -> navigator.navigateBack()
        }

        withContext(Dispatchers.IO) {
            Thread.sleep(500)
            loadingUserPosts = false
        }

    }

    if (postData == null) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Loading()
        }
    } else {
        CardPost(
            fullScreen = true,
            navigator = navigator,
            postData = postData!!.post,
            postsHandlers = postsHandlers,
            user = postData!!.user,
            userRelation = journeyHandlers.getUserRelation(username)
        )
    }
}
