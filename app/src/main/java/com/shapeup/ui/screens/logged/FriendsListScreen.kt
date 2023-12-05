package com.shapeup.ui.screens.logged

import android.view.animation.OvershootInterpolator
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.shapeup.R
import com.shapeup.api.utils.constants.WA_API_URL
import com.shapeup.ui.components.ExpandableContent
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.FormFieldType
import com.shapeup.ui.components.Loading
import com.shapeup.ui.components.SnackbarHelper
import com.shapeup.ui.components.SnackbarType
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.utils.helpers.XPUtils
import com.shapeup.ui.utils.helpers.openWebpage
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyHandlers
import com.shapeup.ui.viewModels.logged.journeyDataMock
import com.shapeup.ui.viewModels.logged.journeyHandlersMock
import io.ktor.http.HttpStatusCode

@Preview
@Composable
fun FriendsListScreenPreview() {
    ShapeUpTheme {
        FriendsListScreen(
            journeyData = journeyDataMock,
            journeyHandlers = journeyHandlersMock,
            navigator = Navigator()
        )
    }
}

@Composable
fun FriendsListScreen(
    journeyData: JourneyData,
    journeyHandlers: JourneyHandlers,
    navigator: Navigator
) {
    var loadingFriends by remember { mutableStateOf(true) }
    var openSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }
    var searchedName by remember { mutableStateOf("") }

    val rowAlpha = remember {
        Animatable(0.3f)
    }

    var friendsFiltered = key(searchedName) {
        journeyData.friends.value.filter { friend ->
            ("${friend.user.firstName} ${friend.user.lastName}").contains(
                searchedName,
                ignoreCase = true
            )
        }
    }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    suspend fun getFriends() {
        loadingFriends = true
        val response = journeyHandlers.getAllFriendship()

        when (response.status) {
            HttpStatusCode.OK -> {
                journeyHandlers.setupFriends(response.data)
                loadingFriends = false
            }

            else -> {
                openSnackbar = true
                snackbarMessage = context.getString(R.string.txt_errors_generic)
                loadingFriends = false
            }
        }
    }

    fun openChat(cellPhone: String) {
        loadingFriends = true
        val phone = "55$cellPhone"
        openWebpage(context, "$WA_API_URL$phone")
        loadingFriends = false
    }

    LaunchedEffect(key1 = true) {
        getFriends()
    }

    LaunchedEffect(key1 = loadingFriends) {
        rowAlpha.animateTo(
            targetValue = if (loadingFriends) 0.3f else 1f,
            animationSpec = tween(
                durationMillis = 200,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
    }

    BackHandler {
        navigator.navigateBack()
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxWidth()
                .padding(
                    bottom = 18.dp,
                    end = 24.dp,
                    start = 24.dp,
                    top = 12.dp
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

            FormField(
                focusManager = focusManager,
                label = stringResource(R.string.txt_chat_search_friends),
                modifier = Modifier.weight(1f),
                type = FormFieldType.SEARCH,
                onValueChange = {
                    searchedName = it
                    friendsFiltered = journeyData.friends.value.filter { friend ->
                        ("${friend.user.firstName} ${friend.user.lastName}").contains(
                            it,
                            ignoreCase = true
                        )
                    }
                },
                value = searchedName
            )
        }

        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            item {
                ExpandableContent(
                    visible = loadingFriends,
                    content = {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Loading()
                        }
                    })
            }
            itemsIndexed(friendsFiltered) { index, it ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .alpha(rowAlpha.value)
                        .padding(
                            horizontal = 24.dp,
                            vertical = 12.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        contentDescription = stringResource(R.string.user_profile_pic),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .border(
                                brush = XPUtils.getBorder(it.user.xp),
                                shape = CircleShape,
                                width = 2.dp
                            )
                            .background(brush = XPUtils.getBorder(it.user.xp))
                            .height(48.dp)
                            .width(48.dp),
                        painter = rememberAsyncImagePainter(it.user.profilePicture)
                    )
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodySmall,
                            text = "${it.user.firstName} ${it.user.lastName}"
                        )
                        if (it.messages.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                color = MaterialTheme.colorScheme.tertiary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodySmall,
                                text = when (
                                    it.messages.last().senderName ==
                                            journeyData.userData.value.username
                                ) {
                                    true ->
                                        "${
                                            stringResource(
                                                R.string.txt_chat_user_last_sender
                                            )
                                        } ${it.messages.last().message}"

                                    else -> it.messages.last().message
                                }
                            )
                        }
                    }

                    IconButton(
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp),
                        onClick = {
                            navigator.navigateArgs("${Screen.Profile.value}/${it.user.username}")
                        }
                    ) {
                        Icon(
                            contentDescription = stringResource(Icon.User.description),
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp),
                            painter = painterResource(Icon.User.value),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    IconButton(
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp),
                        onClick = {
                            if (loadingFriends) return@IconButton
                            openChat(it.user.cellPhone)
                        }
                    ) {
                        Icon(
                            contentDescription = stringResource(Icon.WhatsApp.description),
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp),
                            painter = painterResource(Icon.WhatsApp.value),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
//                    AssistChip(
//                        border = AssistChipDefaults.assistChipBorder(
//                            borderWidth = 0.dp
//                        ),
//                        colors = AssistChipDefaults.assistChipColors(
//                            containerColor = when (it.online) {
//                                true -> MaterialTheme.colorScheme.primary
//                                else -> MaterialTheme.colorScheme.error
//                            },
//                            labelColor = when (it.online) {
//                                true -> MaterialTheme.colorScheme.onPrimary
//                                else -> MaterialTheme.colorScheme.onError
//                            }
//                        ),
//                        label = {
//                            Text(
//                                style = MaterialTheme.typography.bodySmall,
//                                text = when (it.online) {
//                                    true -> stringResource(R.string.txt_chat_online)
//                                    else -> stringResource(R.string.txt_chat_offline)
//                                }
//                            )
//                        },
//                        modifier = Modifier.padding(0.dp),
//                        onClick = {},
//                        shape = RoundedCornerShape(24.dp)
//                    )
                }

//                if (index != friendsFiltered.size - 1) {
                    Divider(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .padding(horizontal = 40.dp)
                    )
//                }
            }
        }
    }

    SnackbarHelper(
        message = snackbarMessage,
        open = openSnackbar,
        openSnackbar = { openSnackbar = it },
        type = SnackbarType.ERROR
    )
}
