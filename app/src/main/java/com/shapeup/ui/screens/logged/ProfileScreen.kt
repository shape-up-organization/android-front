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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.shapeup.R
import com.shapeup.service.users.getUserDataMock
import com.shapeup.ui.components.CardPost
import com.shapeup.ui.components.EPageButtons
import com.shapeup.ui.components.Navbar
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.utils.helpers.XPUtils
import com.shapeup.ui.viewModels.logged.EUserRelation
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyHandlers
import com.shapeup.ui.viewModels.logged.JourneyMappers
import com.shapeup.ui.viewModels.logged.PostsData
import com.shapeup.ui.viewModels.logged.PostsHandlers
import com.shapeup.ui.viewModels.logged.User
import com.shapeup.ui.viewModels.logged.journeyDataMock
import com.shapeup.ui.viewModels.logged.journeyHandlersMock
import com.shapeup.ui.viewModels.logged.postsDataMock
import com.shapeup.ui.viewModels.logged.postsHandlersMock

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun ProfilePreview() {
    ShapeUpTheme {
        ProfileScreen(
            journeyData = journeyDataMock,
            journeyHandlers = journeyHandlersMock,
            navigator = Navigator(),
            postsData = postsDataMock,
            postsHandlers = postsHandlersMock,
            user = JourneyMappers.userDataToUser(getUserDataMock)
        )
    }
}

@Composable
fun ProfileScreen(
    journeyData: JourneyData,
    journeyHandlers: JourneyHandlers,
    navigator: Navigator,
    postsData: PostsData,
    postsHandlers: PostsHandlers,
    user: User
) {
    val userRelation = journeyHandlers.getUserRelation(user.username)

    var tabSelected by remember { mutableIntStateOf(0) }
    var openProfileImageDialog by remember { mutableStateOf(false) }

    val imageSize = LocalConfiguration.current.screenWidthDp.dp / 3
    val expandedProfilePictureSize = LocalConfiguration.current.screenWidthDp * 0.8
    val titles = listOf(
        stringResource(R.string.txt_profile_tab_photos),
        stringResource(R.string.txt_profile_tab_posts)
    )

    BackHandler {
        navigator.navigateBack()
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (userRelation != EUserRelation.USER) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 18.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navigator.navigateBack() }) {
                    Icon(
                        contentDescription = stringResource(Icon.ArrowBack.description),
                        painter = painterResource(Icon.ArrowBack.value),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelLarge,
                    text = "${user.firstName} ${user.lastName}"
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Column {
                when (userRelation) {
                    EUserRelation.USER -> Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = 0.dp,
                                end = 24.dp,
                                start = 24.dp,
                                top = 24.dp
                            )
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    bottom = 16.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .border(
                                        brush = XPUtils.getBorder(user.xp),
                                        shape = CircleShape,
                                        width = 2.dp
                                    )
                                    .clip(CircleShape)
                                    .clickable {
                                        openProfileImageDialog = true
                                    }
                                    .height(64.dp)
                                    .width(64.dp),
                                painter = rememberAsyncImagePainter(
                                    model = user.profilePicture
                                )
                            )

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.labelLarge,
                                    text = "${journeyData.friends.value.size}"
                                )
                                Text(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.labelSmall,
                                    text = stringResource(R.string.txt_profile_level)
                                )
                            }

                            Divider(
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                modifier = Modifier
                                    .height(32.dp)
                                    .width(1.dp)
                            )

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.labelLarge,
                                    text = "${journeyData.friends.value.size}"
                                )
                                Text(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.labelSmall,
                                    text = stringResource(R.string.txt_profile_friends)
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { navigator.navigate(Screen.EditProfile) }) {
                                    Icon(
                                        contentDescription =
                                        stringResource(Icon.Pencil.description),
                                        painter = painterResource(Icon.Pencil.value),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }

                                IconButton(onClick = { navigator.navigate(Screen.Profile) }) {
                                    Icon(
                                        contentDescription = stringResource(
                                            Icon.Settings.description
                                        ),
                                        painter = painterResource(Icon.Settings.value),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }

                        Row {
                            Text(
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.labelLarge,
                                text = "${user.firstName} ${user.lastName}"
                            )
                            Text(
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodySmall,
                                text = " - ${user.username}"
                            )
                        }
                    }

                    else -> Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 24.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .border(
                                        brush = XPUtils.getBorder(user.xp),
                                        shape = CircleShape,
                                        width = 2.dp
                                    )
                                    .clip(CircleShape)
                                    .clickable {
                                        openProfileImageDialog = true
                                    }
                                    .height(64.dp)
                                    .width(64.dp),
                                painter = rememberAsyncImagePainter(
                                    model = user.profilePicture
                                )
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.bodyMedium,
                                    text = "${user.firstName} ${user.lastName}"
                                )
                                Text(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.labelSmall,
                                    text = "${stringResource(R.string.txt_profile_level)} " +
                                        "${user.xp}"
                                )
                            }
                        }

                        AssistChip(
                            border = AssistChipDefaults.assistChipBorder(
                                borderWidth = 0.dp
                            ),
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = when (userRelation) {
                                    EUserRelation.NON_FRIEND -> MaterialTheme.colorScheme.primary
                                    else -> MaterialTheme.colorScheme.error
                                },
                                labelColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            label = {
                                Text(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    style = MaterialTheme.typography.bodyMedium,
                                    text = run {
                                        when (userRelation) {
                                            EUserRelation.FRIEND -> stringResource(
                                                R.string.txt_profile_remove_friend
                                            )

                                            EUserRelation.NON_FRIEND -> stringResource(
                                                R.string.txt_profile_add_friend
                                            )

                                            EUserRelation.NON_FRIEND_RECEIVED -> stringResource(
                                                R.string.txt_profile_refuse_friend
                                            )

                                            EUserRelation.NON_FRIEND_REQUESTED -> stringResource(
                                                R.string.txt_profile_cancel_friend
                                            )

                                            else -> stringResource(
                                                R.string.txt_profile_add_friend
                                            )
                                        }
                                    }
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Icon(
                                    contentDescription = stringResource(
                                        when (userRelation) {
                                            EUserRelation.NON_FRIEND -> Icon.Add.description
                                            else -> Icon.Decline.description
                                        }
                                    ),
                                    modifier = Modifier
                                        .height(16.dp)
                                        .width(16.dp),
                                    painter = painterResource(
                                        when (userRelation) {
                                            EUserRelation.NON_FRIEND -> Icon.Add.value
                                            else -> Icon.Decline.value
                                        }
                                    ),
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            },
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(24.dp)
                        )
                    }
                }

                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 24.dp,
                            vertical = 16.dp
                        ),
                    style = MaterialTheme.typography.bodySmall,
                    text = "Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet. " +
                        "Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet. " +
                        "Lorem ipsum dolor sit amet. "
                )

                TabRow(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedTabIndex = tabSelected
                ) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            onClick = { tabSelected = index },
                            selected = tabSelected == index,
                            text = {
                                Text(
                                    text = title,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        )
                    }
                }
            }

            when (tabSelected) {
                0 -> LazyVerticalGrid(
                    columns = GridCells.Fixed(3)
                ) {
                    items(
                        postsData.specificPosts.value.filter {
                            it.photoUrls.isNotEmpty()
                        }
                    ) {
                        Image(
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clickable {
                                    navigator.navigateArgs(
                                        "${Screen.Post.value}/${user.username}/${it.id}"
                                    )
                                }
                                .height(imageSize),
                            painter = rememberAsyncImagePainter(model = it.photoUrls[0])
                        )
                    }
                }

                1 -> LazyVerticalGrid(
                    columns = GridCells.Fixed(1)
                ) {
                    items(
                        postsData.specificPosts.value.filter {
                            it.photoUrls.isEmpty()
                        }
                    ) {
                        CardPost(
                            compactPost = true,
                            getComments = postsHandlers.getCommentsByPostId,
                            navigator = navigator,
                            postData = it,
                            sendComment = postsHandlers.sendComment,
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
            }
        }

        Navbar(
            activePage = EPageButtons.PROFILE,
            data = journeyData,
            navigator = navigator
        )
    }

    if (openProfileImageDialog) {
        Dialog(onDismissRequest = { openProfileImageDialog = false }) {
            Image(
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .border(
                        brush = XPUtils.getBorder(user.xp),
                        shape = CircleShape,
                        width = 2.dp
                    )
                    .clip(CircleShape)
                    .height(expandedProfilePictureSize.dp)
                    .width(expandedProfilePictureSize.dp),
                painter = rememberAsyncImagePainter(
                    model = user.profilePicture
                )
            )
        }
    }
}
