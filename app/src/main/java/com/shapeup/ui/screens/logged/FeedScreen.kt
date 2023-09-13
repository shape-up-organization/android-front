package com.shapeup.ui.screens.logged

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.shapeup.MainActivity
import com.shapeup.R
import com.shapeup.service.friends.getAllFriendshipMock
import com.shapeup.service.posts.getPostsMock
import com.shapeup.service.users.getUserDataMock
import com.shapeup.ui.components.Carousel
import com.shapeup.ui.components.EPageButtons
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.FormFieldType
import com.shapeup.ui.components.Navbar
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.utils.helpers.XPUtils
import com.shapeup.ui.viewModels.logged.EUserRelation
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyHandlers
import kotlin.system.exitProcess

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun FeedPreview() {
    ShapeUpTheme {
        FeedScreen(
            data = JourneyData(
                friends = mutableStateOf(emptyList()),
                selectedUser = mutableStateOf(null),
                userData = mutableStateOf(getUserDataMock)
            ),
            handlers = JourneyHandlers(
                getUserRelation = { EUserRelation.USER },
                logOut = {}
            ),
            navigator = Navigator()
        )
    }
}

@Composable
fun FeedScreen(
    data: JourneyData,
    handlers: JourneyHandlers,
    navigator: Navigator
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
            getPostsMock.map {
                val user = getAllFriendshipMock.find { user ->
                    user.username == it.username
                } ?: data.userData.value

                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 24.dp,
                                vertical = 8.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            modifier = Modifier
                                .height(40.dp)
                                .width(40.dp),
                            onClick = {
                                if (handlers.getUserRelation(user.username) != EUserRelation.USER) {
                                    data.selectedUser.value = user
                                }
                                navigator.navigateClean(Screen.Profile)
                            }
                        ) {
                            Image(
                                contentDescription = stringResource(R.string.user_profile_pic),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .border(
                                        brush = XPUtils.getBorder(user.xp),
                                        shape = CircleShape,
                                        width = 2.dp
                                    )
                                    .background(brush = XPUtils.getBorder(user.xp))
                                    .height(32.dp)
                                    .width(32.dp),
                                painter = rememberAsyncImagePainter(user.profilePicture)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 1,
                            modifier = Modifier
                                .clickable {
                                    data.selectedUser.value = user
                                    navigator.navigate(Screen.Profile)
                                }
                                .weight(1f),
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodySmall,
                            text = "${user.firstName} ${user.lastName}"
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp),
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(
                                contentDescription = stringResource(Icon.More.description),
                                modifier = Modifier
                                    .height(4.dp)
                                    .width(16.dp),
                                painter = painterResource(Icon.More.value),
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }

                    Carousel(data = it.photoUrls)

                    if (it.photoUrls.isEmpty()) {
                        it.description?.let {
                            Text(
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 8,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp),
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodySmall,
                                text = it
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 24.dp,
                                vertical = 8.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            modifier = Modifier
                                .height(32.dp)
                                .width(32.dp),
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(
                                contentDescription = stringResource(
                                    when (it.liked) {
                                        true -> Icon.HeartFilled.description
                                        else -> Icon.HeartOutlined.description
                                    }
                                ),
                                modifier = Modifier
                                    .height(24.dp)
                                    .width(24.dp),
                                painter = painterResource(
                                    when (it.liked) {
                                        true -> Icon.HeartFilled.value
                                        else -> Icon.HeartOutlined.value
                                    }
                                ),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        Text(
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 1,
                            modifier = Modifier.weight(1f),
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.labelMedium,
                            text = "${it.countLike} ${stringResource(R.string.txt_feed_likes)}"
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        AssistChip(
                            border = AssistChipDefaults.assistChipBorder(
                                borderWidth = 0.dp
                            ),
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                labelColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            label = {
                                Text(
                                    style = MaterialTheme.typography.labelMedium,
                                    text = "${it.countComments} " +
                                            stringResource(R.string.txt_feed_comments)
                                )
                            },
                            modifier = Modifier.padding(0.dp),
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(24.dp)
                        )
                    }

                    if (it.photoUrls.isNotEmpty()) {
                        it.description?.let {
                            Text(
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 2,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp),
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodySmall,
                                text = it
                            )
                        }
                    }
                }

                Spacer(
                    modifier = with(Modifier) {
                        height(
                            when {
                                it.description.isNullOrBlank() ||
                                        it.photoUrls.isNullOrEmpty() -> 4.dp

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
            data = data,
            navigator = navigator
        )
    }
}
