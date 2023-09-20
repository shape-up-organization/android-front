package com.shapeup.ui.screens.logged

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.MainActivity
import com.shapeup.R
import com.shapeup.service.friends.getAllFriendshipMock
import com.shapeup.service.posts.getPostsMock
import com.shapeup.service.users.getUserDataMock
import com.shapeup.ui.components.CardPost
import com.shapeup.ui.components.EPageButtons
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.FormFieldType
import com.shapeup.ui.components.Navbar
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.logged.EUserRelation
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyHandlers
import com.shapeup.ui.viewModels.logged.PostsData
import kotlin.system.exitProcess

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun FeedPreview() {
    ShapeUpTheme {
        FeedScreen(
            journeyData = JourneyData(
                friends = mutableStateOf(emptyList()),
                userData = mutableStateOf(getUserDataMock)
            ),
            journeyHandlers = JourneyHandlers(
                getFriends = { getAllFriendshipMock },
                getUser = { getUserDataMock },
                getUserRelation = { EUserRelation.USER },
                logOut = {}
            ),
            postsData = PostsData(
                posts = mutableStateOf(getPostsMock),
                specificPosts = mutableStateOf(emptyList())
            ),
            navigator = Navigator()
        )
    }
}

@Composable
fun FeedScreen(
    journeyData: JourneyData,
    journeyHandlers: JourneyHandlers,
    navigator: Navigator,
    postsData: PostsData
) {
    val focusManager = LocalFocusManager.current

    BackHandler {
        MainActivity().finish()
        exitProcess(0)
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
                    bottom = 16.dp,
                    end = 16.dp,
                    start = 16.dp,
                    top = 8.dp
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

            FormField(
                focusManager = focusManager,
                label = stringResource(R.string.txt_feed_search_input),
                type = FormFieldType.SEARCH,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(24.dp))

            IconButton(
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp),
                onClick = { navigator.navigate(Screen.Feed) }
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

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            postsData.posts.value.map {
                val user = journeyHandlers.getUser(it.username) ?: return
                val userRelation = journeyHandlers.getUserRelation(it.username)

                CardPost(
                    postData = it,
                    navigator = navigator,
                    user = user,
                    userRelation = userRelation
                )

                Spacer(
                    modifier = with(Modifier) {
                        height(
                            when {
                                it.description.isNullOrBlank() ||
                                    it.photoUrls.isEmpty() -> 4.dp

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
}
