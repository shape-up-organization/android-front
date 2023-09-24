package com.shapeup.ui.screens.logged

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.shapeup.R
import com.shapeup.service.friends.getAllFriendshipMock
import com.shapeup.ui.components.FormField
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.utils.helpers.XPUtils
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyHandlers
import com.shapeup.ui.viewModels.logged.journeyDataMock
import com.shapeup.ui.viewModels.logged.journeyHandlersMock

@Preview
@Composable
fun ChatScreenPreview() {
    ShapeUpTheme {
        ChatScreen(
            journeyData = journeyDataMock,
            journeyHandlers = journeyHandlersMock,
            navigator = Navigator(),
            username = getAllFriendshipMock[0].username
        )
    }
}

@Composable
fun ChatScreen(
    journeyData: JourneyData,
    journeyHandlers: JourneyHandlers,
    navigator: Navigator,
    username: String
) {
    var expandOptionsMenu by remember { mutableStateOf(false) }
    var messageText by remember { mutableStateOf("") }

    val userData = journeyData.friends.value.find { friend ->
        friend.user.username == username
    }!!
    val messages = userData.messages

    val messagesColumnState = rememberLazyListState()

    val focusManager = LocalFocusManager.current

    LaunchedEffect(messages.size) {
        messagesColumnState.animateScrollToItem(messages.size - 1)
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
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .fillMaxWidth()
                .padding(
                    bottom = 20.dp,
                    end = 24.dp,
                    start = 24.dp,
                    top = 20.dp
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

            Image(
                contentDescription = stringResource(R.string.user_profile_pic),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .border(
                        brush = XPUtils.getBorder(userData.user.xp),
                        shape = CircleShape,
                        width = 2.dp
                    )
                    .clickable {
                        navigator
                            .navigateArgs("${Screen.Profile.value}/${userData.user.username}")
                    }
                    .background(brush = XPUtils.getBorder(userData.user.xp))
                    .height(48.dp)
                    .width(48.dp),
                painter = rememberAsyncImagePainter(userData.user.profilePicture)
            )

            Column(
                modifier = Modifier
                    .clickable {
                        navigator
                            .navigateArgs("${Screen.Profile.value}/${userData.user.username}")
                    }
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelLarge,
                    text = "${userData.user.firstName} ${userData.user.lastName}"
                )
                AssistChip(
                    border = AssistChipDefaults.assistChipBorder(
                        borderWidth = 0.dp
                    ),
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = when (userData.online) {
                            true -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.error
                        },
                        labelColor = when (userData.online) {
                            true -> MaterialTheme.colorScheme.onPrimary
                            else -> MaterialTheme.colorScheme.onError
                        }
                    ),
                    label = {
                        Text(
                            style = MaterialTheme.typography.bodySmall,
                            text = when (userData.online) {
                                true -> stringResource(R.string.txt_chat_online)
                                else -> stringResource(R.string.txt_chat_offline)
                            }
                        )
                    },
                    modifier = Modifier
                        .padding(0.dp)
                        .height(24.dp),
                    onClick = {},
                    shape = RoundedCornerShape(24.dp)
                )
            }

            Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                IconButton(
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp),
                    onClick = { expandOptionsMenu = true }
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
                DropdownMenu(
                    expanded = expandOptionsMenu,
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                    onDismissRequest = { expandOptionsMenu = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            navigator
                                .navigateArgs("${Screen.Profile.value}/${userData.user.username}")
                        },
                        text = {
                            Text(
                                color = MaterialTheme.colorScheme.onBackground,
                                text = stringResource(
                                    R.string.txt_profile_see_profile
                                )
                            )
                        }
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            state = messagesColumnState
        ) {
            items(messages) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .align(
                                alignment = when (it.senderName) {
                                    userData.user.username -> Alignment.Start
                                    else -> Alignment.End
                                }
                            )
                            .padding(
                                bottom = 8.dp,
                                end = when (it.senderName) {
                                    userData.user.username -> 64.dp
                                    else -> 24.dp
                                },
                                start = when (it.senderName) {
                                    userData.user.username -> 24.dp
                                    else -> 64.dp
                                },
                                top = 8.dp
                            )
                            .background(
                                MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyMedium,
                            text = it.message
                        )
                        Text(
                            color = MaterialTheme.colorScheme.tertiary,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.align(alignment = Alignment.End),
                            text = it.date,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }

        Divider(
            color = MaterialTheme.colorScheme.tertiaryContainer,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth()
                .padding(
                    bottom = 20.dp,
                    end = 24.dp,
                    start = 24.dp,
                    top = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FormField(
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Default,
                    keyboardType = KeyboardType.Ascii
                ),
                label = stringResource(R.string.txt_profile_sheet_send_comment),
                maxLines = 3,
                modifier = Modifier.weight(1f),
                onValueChange = { messageText = it },
                value = messageText
            )
            IconButton(
                enabled = messageText.isNotBlank(),
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp),
                onClick = {
                    journeyHandlers.sendMessage(messageText, userData.user.username)
                    messageText = ""
                }
            ) {
                Icon(
                    contentDescription = stringResource(Icon.Send.description),
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp),
                    painter = painterResource(Icon.Send.value),
                    tint = when (messageText.isNotBlank()) {
                        true -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.tertiary
                    }
                )
            }
        }
    }
}
