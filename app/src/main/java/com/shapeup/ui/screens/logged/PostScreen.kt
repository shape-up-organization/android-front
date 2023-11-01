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
import com.shapeup.api.services.posts.Post
import com.shapeup.api.services.users.getUserDataMock
import com.shapeup.ui.components.CardPost
import com.shapeup.ui.components.Loading
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.logged.EUserRelation
import com.shapeup.ui.viewModels.logged.JourneyMappers
import com.shapeup.ui.viewModels.logged.PostsHandlers
import com.shapeup.ui.viewModels.logged.User
import com.shapeup.ui.viewModels.logged.postsHandlersMock
import io.ktor.http.HttpStatusCode

@Preview
@Composable
fun PostScreenPreview() {
    ShapeUpTheme {
        PostScreen(
            navigator = Navigator(),
            postId = "1",
            postsHandlers = postsHandlersMock,
            user = JourneyMappers.userDataToUser(getUserDataMock),
            userRelation = EUserRelation.USER
        )
    }
}

@Composable
fun PostScreen(
    navigator: Navigator,
    postId: String,
    postsHandlers: PostsHandlers,
    user: User,
    userRelation: EUserRelation
) {
    var postData by remember { mutableStateOf<Post?>(null) }

    LaunchedEffect(key1 = true) {
        val response = postsHandlers.getPostById(postId)

        when (response.status) {
            HttpStatusCode.OK -> postData = response.data

            else -> navigator.navigateBack()
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
            postData = postData!!,
            postsHandlers = postsHandlers,
            user = user,
            userRelation = userRelation
        )
    }
}
