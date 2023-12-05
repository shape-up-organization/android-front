package com.shapeup.ui.screens.logged

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.api.services.posts.GetPostsPaginatedPayload
import com.shapeup.ui.components.CardPost
import com.shapeup.ui.components.EPageButtons
import com.shapeup.ui.components.ExpandableContent
import com.shapeup.ui.components.Loading
import com.shapeup.ui.components.Navbar
import com.shapeup.ui.components.SnackbarHelper
import com.shapeup.ui.components.SnackbarType
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.auth.AuthHandlers
import com.shapeup.ui.viewModels.auth.authHandlersMock
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyHandlers
import com.shapeup.ui.viewModels.logged.PostView
import com.shapeup.ui.viewModels.logged.PostsData
import com.shapeup.ui.viewModels.logged.PostsHandlers
import com.shapeup.ui.viewModels.logged.journeyDataMock
import com.shapeup.ui.viewModels.logged.journeyHandlersMock
import com.shapeup.ui.viewModels.logged.postsDataMock
import com.shapeup.ui.viewModels.logged.postsHandlersMock
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Preview
@Composable
fun FeedScreenPreview() {
    ShapeUpTheme {
        FeedScreen(
            authHandlers = authHandlersMock,
            journeyData = journeyDataMock,
            journeyHandlers = journeyHandlersMock,
            postsData = postsDataMock,
            postsHandlers = postsHandlersMock,
            navigator = Navigator()
        )
    }
}

@Composable
fun FeedScreen(
    authHandlers: AuthHandlers,
    journeyData: JourneyData,
    journeyHandlers: JourneyHandlers,
    navigator: Navigator,
    postsData: PostsData,
    postsHandlers: PostsHandlers
) {
    var loadingPosts by remember { mutableStateOf(journeyData.initialLoad.value) }
    var openSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    suspend fun getFriends() {
        val response = journeyHandlers.getAllFriendship()

        when (response.status) {
            HttpStatusCode.OK -> {
                journeyHandlers.setupFriends(response.data)
            }

            else -> {
                openSnackbar = true
                snackbarMessage = context.getString(R.string.txt_errors_generic)
            }
        }
    }

    suspend fun getPosts() {
        loadingPosts = true

        val response = postsHandlers.getPosts(
            GetPostsPaginatedPayload(
                page = 0,
                size = 20
            ),
        )

        when (response.status) {
            HttpStatusCode.OK -> {
                val postViews = mutableListOf<PostView>()

                response.data!!.forEach {
                    val userResponse = journeyHandlers.getUser(it.username)

                    when (userResponse.status) {
                        HttpStatusCode.OK -> {
                            if (userResponse.data != null) {
                                postViews.add(
                                    PostView(
                                        post = it,
                                        user = userResponse.data
                                    )
                                )
                            }
                        }

                        HttpStatusCode.NotFound -> {
                            openSnackbar = true
                            snackbarMessage = context.getString(R.string.txt_errors_user_not_found)
                        }

                        else -> {
                            openSnackbar = true
                            snackbarMessage = context.getString(R.string.txt_errors_generic)
                        }
                    }
                }

                postsData.posts.value = postViews
            }

            HttpStatusCode.NoContent -> postsData.posts.value = emptyList()

            else -> {
                openSnackbar = true
                snackbarMessage = context.getString(R.string.txt_errors_generic)
            }
        }

        withContext(Dispatchers.IO) {
            Thread.sleep(500)
            loadingPosts = false
        }
    }

    fun deletePost(postId: String) {
        coroutine.launch {
            loadingPosts = true

            val response = postsHandlers.deletePost(postId)

            if (response.status == HttpStatusCode.OK) {
                getPosts()
                journeyHandlers.updateXp()
            } else {
                loadingPosts = false
            }
        }
    }

    LaunchedEffect(key1 = true) {
        if (journeyData.initialLoad.value) {
            journeyHandlers.setupUser(null)
            getFriends()
            journeyData.initialLoad.value = false
        }
        getPosts()
    }


    BackHandler {
        journeyData.initialLoad.value = true
        authHandlers.signOut()
        navigator.navigateClean(Screen.Splash)
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxWidth()
                .padding(
                    bottom = 20.dp,
                    end = 16.dp,
                    start = 16.dp,
                    top = 20.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp),
                onClick = { navigator.navigate(Screen.Notification) }
            ) {
                Icon(
                    contentDescription = stringResource(Icon.Notifications.description),
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp),
                    painter = painterResource(Icon.Notifications.value),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(size = 16.dp)
                    )
                    .width(240.dp)
                    .height(60.dp)
                    .padding(start = 16.dp)
                    .clickable {
                        navigator.navigate(Screen.Search)
                    }
            ) {
                Icon(
                    contentDescription = stringResource(Icon.Search.description),
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    painter = painterResource(Icon.Search.value),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = stringResource(R.string.txt_feed_search_input),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            IconButton(
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp),
                onClick = { navigator.navigate(Screen.FriendsList) }
            ) {
                Icon(
                    contentDescription = stringResource(Icon.Chats.description),
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp),
                    painter = painterResource(Icon.Chats.value),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                ExpandableContent(
                    visible = loadingPosts,
                    content = {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Loading()
                        }
                    })
            }
            items(postsData.posts.value) {
                CardPost(
                    deletePost = { postId -> deletePost(postId) },
                    navigator = navigator,
                    postData = it.post,
                    postsHandlers = postsHandlers,
                    journeyHandlers = journeyHandlers,
                    user = it.user,
                    userRelation = journeyHandlers.getUserRelationByUsername(it.post.username)
                )

                Spacer(
                    modifier = with(Modifier) {
                        height(
                            when {
                                it.post.description.isNullOrBlank() ||
                                        it.post.photoUrls.isEmpty() -> 4.dp

                                else -> 32.dp
                            }
                        )
                    }
                )

                Divider(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp),
                    thickness = 1.dp
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Navbar(
            activePage = EPageButtons.HOME,
            data = journeyData,
            navigator = navigator
        )
    }

    SnackbarHelper(
        message = snackbarMessage,
        open = openSnackbar,
        openSnackbar = { openSnackbar = it },
        type = SnackbarType.ERROR
    )
}
