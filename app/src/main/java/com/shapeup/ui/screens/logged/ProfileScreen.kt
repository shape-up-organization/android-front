package com.shapeup.ui.screens.logged

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.shapeup.R
import com.shapeup.api.services.friends.GenericFriendshipStatement
import com.shapeup.api.services.posts.GetPostsByUsernamePayload
import com.shapeup.api.services.users.UserSearch
import com.shapeup.api.services.users.getUserDataMock
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
import com.shapeup.ui.utils.helpers.XPUtils
import com.shapeup.ui.viewModels.logged.EUserRelation
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyHandlers
import com.shapeup.ui.viewModels.logged.PostsData
import com.shapeup.ui.viewModels.logged.PostsHandlers
import com.shapeup.ui.viewModels.logged.journeyDataMock
import com.shapeup.ui.viewModels.logged.journeyHandlersMock
import com.shapeup.ui.viewModels.logged.postsDataMock
import com.shapeup.ui.viewModels.logged.postsHandlersMock
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch

@Preview
@Composable
fun ProfileScreenPreview() {
    ShapeUpTheme {
        ProfileScreen(
            journeyData = journeyDataMock,
            journeyHandlers = journeyHandlersMock,
            navigator = Navigator(),
            postsData = postsDataMock,
            postsHandlers = postsHandlersMock,
            username = getUserDataMock.username
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
    username: String
) {
    var userRelation by remember { mutableStateOf(EUserRelation.USER) }

    var loadingUser by remember { mutableStateOf(true) }
    var loadingUserRelation by remember { mutableStateOf(false) }
    var loadingPosts by remember { mutableStateOf(false) }
    var user by remember { mutableStateOf<UserSearch?>(null) }
    var tabSelected by remember { mutableIntStateOf(0) }
    var openProfileImageDialog by remember { mutableStateOf(false) }
    var openSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    val coroutine = rememberCoroutineScope()

    val context = LocalContext.current
    val imageSize = LocalConfiguration.current.screenWidthDp.dp / 3
    val expandedProfilePictureSize = LocalConfiguration.current.screenWidthDp * 0.8
    val titles = listOf(
        stringResource(R.string.txt_profile_tab_photos),
        stringResource(R.string.txt_profile_tab_posts)
    )

    suspend fun getPosts() {
        loadingPosts = true

        val response = postsHandlers.getUserPosts(
            GetPostsByUsernamePayload(
                username = username,
                page = 0,
                size = 20
            ),
        )

        loadingPosts = false

        when (response.status) {
            HttpStatusCode.OK -> {
            }

            HttpStatusCode.NoContent -> postsData.specificPosts.value = emptyList()

            else -> {
                openSnackbar = true
                snackbarMessage = context.getString(R.string.txt_errors_generic)
            }
        }

        loadingPosts = false
    }

    fun getUser() {
        coroutine.launch {
            val response = journeyHandlers.getUser(username)

            when (response.status) {
                HttpStatusCode.OK -> {
                    if (response.data != null) {
                        user = response.data
                        getPosts()
                        userRelation = journeyHandlers.getUserRelationByUser(response.data)
                    } else {
                        navigator.navigateBack()
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

            loadingUser = false
            loadingUserRelation = false
        }
    }

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

    fun executeUserRelationAction(userRelation: EUserRelation) {
        if (user == null || userRelation == EUserRelation.USER) return

        coroutine.launch {
            loadingUserRelation = true
            val response = when (userRelation) {
                EUserRelation.FRIEND -> journeyHandlers.deleteFriend(user!!.username).status

                EUserRelation.NON_FRIEND -> journeyHandlers.requestFriendship(user!!.username).status

                EUserRelation.NON_FRIEND_RECEIVED -> journeyHandlers.acceptFriendshipRequest(user!!.username).status

                EUserRelation.NON_FRIEND_REQUESTED -> journeyHandlers.deleteFriendshipRequest(user!!.username).status

                else -> {
                    GenericFriendshipStatement(
                        status = HttpStatusCode.UnprocessableEntity
                    ).status
                }
            }

            when (response) {
                HttpStatusCode.UnprocessableEntity -> {}

                HttpStatusCode.Created -> {
                    getUser()
                    when (userRelation) {
                        EUserRelation.FRIEND -> {
                            getFriends()
                        }

                        EUserRelation.NON_FRIEND_RECEIVED -> {
                            getFriends()
                        }

                        else -> {}
                    }
                    journeyHandlers.updateXp()
                }

                HttpStatusCode.NoContent -> {
                    getUser()
                    when (userRelation) {
                        EUserRelation.FRIEND -> {
                            getFriends()
                        }

                        EUserRelation.NON_FRIEND_RECEIVED -> {
                            getFriends()
                        }

                        else -> {}
                    }
                    journeyHandlers.updateXp()
                }

                else -> {
                    openSnackbar = true
                    snackbarMessage = context.getString(R.string.txt_errors_generic)
                    loadingUserRelation = false
                }
            }
        }
    }

    fun refuseFriendshipRequest() {
        coroutine.launch {
            loadingUserRelation = true
            val response = journeyHandlers.deleteFriendshipRequest(user!!.username)

            when (response.status) {
                HttpStatusCode.NoContent -> {
                    getUser()
                }

                else -> {
                    openSnackbar = true
                    snackbarMessage = context.getString(R.string.txt_errors_generic)
                }
            }
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
        loadingUser = true

        getUser()
    }

    BackHandler {
        navigator.navigateBack()
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (loadingUser || user == null) {
            ExpandableContent(
                visible = true,
                content = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(top = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Loading()
                    }
                })
        } else {
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
                        text = "${user!!.firstName} ${user!!.lastName}"
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
                                            brush = XPUtils.getBorder(user!!.xp),
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
                                        model = user!!.profilePicture
                                    )
                                )

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        style = MaterialTheme.typography.labelLarge,
                                        text = "${XPUtils.getLevel(user!!.xp)}"
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

                                    IconButton(onClick = { navigator.navigate(Screen.Settings) }) {
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
                                    text = "${user!!.firstName} ${user!!.lastName}"
                                )
                                Text(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.bodySmall,
                                    text = " - ${user!!.username}"
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
                                            brush = XPUtils.getBorder(user!!.xp),
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
                                        model = user!!.profilePicture
                                    )
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.bodyMedium,
                                        text = "${user!!.firstName} ${user!!.lastName}"
                                    )
                                    Text(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        style = MaterialTheme.typography.labelSmall,
                                        text = "${stringResource(R.string.txt_profile_level)} " +
                                                "${XPUtils.getLevel(user!!.xp)}"
                                    )
                                }
                            }

                            Column {
                                AssistChip(
                                    border = AssistChipDefaults.assistChipBorder(
                                        borderWidth = 0.dp
                                    ),
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = when (userRelation) {
                                            EUserRelation.NON_FRIEND -> MaterialTheme.colorScheme.primary
                                            EUserRelation.NON_FRIEND_RECEIVED -> MaterialTheme.colorScheme.primary
                                            else -> MaterialTheme.colorScheme.error
                                        },
                                        labelColor = MaterialTheme.colorScheme.onPrimary
                                    ),
                                    enabled = !loadingUserRelation,
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
                                                        R.string.txt_profile_accept_friend
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
                                    },
                                    trailingIcon = {
                                        Icon(
                                            contentDescription = stringResource(
                                                when (userRelation) {
                                                    EUserRelation.NON_FRIEND -> Icon.Add.description
                                                    EUserRelation.NON_FRIEND_RECEIVED -> Icon.Add.description
                                                    else -> Icon.Decline.description
                                                }
                                            ),
                                            modifier = Modifier
                                                .height(16.dp)
                                                .width(16.dp),
                                            painter = if (userRelation == EUserRelation.NON_FRIEND
                                                || userRelation == EUserRelation.NON_FRIEND_RECEIVED
                                            )
                                                painterResource(Icon.Add.value)
                                            else painterResource(Icon.Decline.value),
                                            tint = MaterialTheme.colorScheme.onPrimary
                                        )
                                    },
                                    onClick = { executeUserRelationAction(userRelation) },
                                    shape = RoundedCornerShape(24.dp)
                                )

                                if (userRelation == EUserRelation.NON_FRIEND_RECEIVED) {
                                    AssistChip(
                                        border = AssistChipDefaults.assistChipBorder(
                                            borderWidth = 0.dp
                                        ),
                                        colors = AssistChipDefaults.assistChipColors(
                                            containerColor = MaterialTheme.colorScheme.error,
                                            labelColor = MaterialTheme.colorScheme.onPrimary
                                        ),
                                        enabled = !loadingUserRelation,
                                        label = {
                                            Text(
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                style = MaterialTheme.typography.bodyMedium,
                                                text = stringResource(
                                                    R.string.txt_profile_refuse_friend
                                                )
                                            )
                                        },
                                        trailingIcon = {
                                            Icon(
                                                contentDescription = stringResource(Icon.Decline.description),
                                                modifier = Modifier
                                                    .height(16.dp)
                                                    .width(16.dp),
                                                painter = painterResource(Icon.Decline.value),
                                                tint = MaterialTheme.colorScheme.onPrimary
                                            )
                                        },
                                        onClick = { refuseFriendshipRequest() },
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                }
                            }
                        }
                    }

                    if (user?.biography != null && user?.biography != "") {
                        Text(
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 24.dp,
                                    vertical = 16.dp
                                ),
                            style = MaterialTheme.typography.bodySmall,
                            text = user!!.biography!!
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

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
                                            "${Screen.Post.value}/${user!!.username}/${it.id}"
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
                                deletePost = { postId -> deletePost(postId) },
                                navigator = navigator,
                                postData = it,
                                postsHandlers = postsHandlers,
                                journeyHandlers = journeyHandlers,
                                user = user!!,
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
        }

        Navbar(
            activePage = EPageButtons.PROFILE,
            data = journeyData,
            navigator = navigator
        )
    }

    if (openProfileImageDialog && user != null) {
        Dialog(onDismissRequest = { openProfileImageDialog = false }) {
            Image(
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .border(
                        brush = XPUtils.getBorder(user!!.xp),
                        shape = CircleShape,
                        width = 2.dp
                    )
                    .clip(CircleShape)
                    .height(expandedProfilePictureSize.dp)
                    .width(expandedProfilePictureSize.dp),
                painter = rememberAsyncImagePainter(
                    model = user!!.profilePicture
                )
            )
        }
    }

    SnackbarHelper(
        message = snackbarMessage,
        open = openSnackbar,
        openSnackbar = { openSnackbar = it },
        type = SnackbarType.ERROR
    )
}
