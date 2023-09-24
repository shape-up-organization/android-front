package com.shapeup.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.shapeup.R
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.utils.helpers.XPUtils
import com.shapeup.ui.viewModels.logged.Comment
import com.shapeup.ui.viewModels.logged.EUserRelation
import com.shapeup.ui.viewModels.logged.Post
import com.shapeup.ui.viewModels.logged.User

@Composable
fun CardPost(
    compactPost: Boolean = false,
    fullScreen: Boolean = false,
    getComments: (String) -> List<Comment>?,
    sendComment: (postId: String, commentMessage: String) -> Unit,
    navigator: Navigator,
    postData: Post,
    user: User,
    userRelation: EUserRelation
) {
    var comments by remember {
        mutableStateOf<List<Comment>>(emptyList())
    }

    val expandCommentsBottomSheet = remember { mutableStateOf(false) }
    var expandOptionsMenu by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                when (fullScreen) {
                    true ->
                        Modifier
                            .background(color = MaterialTheme.colorScheme.background)
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState())

                    else -> Modifier
                }
            )
            .then(
                when (compactPost) {
                    true ->
                        Modifier
                            .padding(all = 16.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(
                                bottom = 10.dp,
                                end = 16.dp,
                                start = 16.dp,
                                top = 24.dp
                            )

                    else -> Modifier
                }
            )
    ) {
        if (!compactPost) {
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
                if (fullScreen) {
                    IconButton(onClick = { navigator.navigateBack() }) {
                        Icon(
                            contentDescription = stringResource(Icon.ArrowBack.description),
                            painter = painterResource(Icon.ArrowBack.value),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                IconButton(
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp),
                    onClick = {
                        navigator.navigateArgs("${Screen.Profile.value}/${user.username}")
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
                            navigator.navigateArgs("${Screen.Profile.value}/${user.username}")
                        }
                        .weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    text = "${user.firstName} ${user.lastName}"
                )

                Spacer(modifier = Modifier.width(8.dp))

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
                                navigator.navigateArgs("${Screen.Profile.value}/${user.username}")
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

                        if (userRelation == EUserRelation.USER) {
                            DropdownMenuItem(
                                onClick = { /*TODO: */ },
                                text = {
                                    Text(
                                        color = MaterialTheme.colorScheme.error,
                                        text = stringResource(
                                            R.string.txt_feed_delete
                                        )
                                    )
                                }
                            )
                        }

                        if (userRelation == EUserRelation.FRIEND) {
                            DropdownMenuItem(
                                onClick = { /*TODO: */ },
                                text = {
                                    Text(
                                        color = MaterialTheme.colorScheme.error,
                                        text = stringResource(
                                            R.string.txt_profile_remove_friend
                                        )
                                    )
                                }
                            )
                        }

                        if (userRelation == EUserRelation.NON_FRIEND) {
                            DropdownMenuItem(
                                onClick = { /*TODO: */ },
                                text = {
                                    Text(
                                        color = MaterialTheme.colorScheme.primary,
                                        text = stringResource(
                                            R.string.txt_profile_add_friend
                                        )
                                    )
                                }
                            )
                        }

                        if (userRelation == EUserRelation.NON_FRIEND_RECEIVED) {
                            DropdownMenuItem(
                                onClick = { /*TODO: */ },
                                text = {
                                    Text(
                                        color = MaterialTheme.colorScheme.error,
                                        text = stringResource(
                                            R.string.txt_profile_refuse_friend
                                        )
                                    )
                                }
                            )
                        }

                        if (userRelation == EUserRelation.NON_FRIEND_REQUESTED) {
                            DropdownMenuItem(
                                onClick = { /*TODO: */ },
                                text = {
                                    Text(
                                        color = MaterialTheme.colorScheme.error,
                                        text = stringResource(
                                            R.string.txt_profile_cancel_friend
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

        Carousel(data = postData.photoUrls)

        if (postData.photoUrls.isEmpty()) {
            postData.description?.let {
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = when (fullScreen) {
                        true -> Int.MAX_VALUE
                        else -> 8
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    overflow = when (fullScreen) {
                        true -> TextOverflow.Visible
                        else -> TextOverflow.Ellipsis
                    },
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
                        when (postData.liked) {
                            true -> Icon.HeartFilled.description
                            else -> Icon.HeartOutlined.description
                        }
                    ),
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp),
                    painter = painterResource(
                        when (postData.liked) {
                            true -> Icon.HeartFilled.value
                            else -> Icon.HeartOutlined.value
                        }
                    ),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium,
                text = "${postData.countLike} ${stringResource(R.string.txt_feed_likes)}"
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
                        text = "${postData.countComments} " +
                            stringResource(R.string.txt_feed_comments)
                    )
                },
                modifier = Modifier.padding(0.dp),
                onClick = {
                    expandCommentsBottomSheet.value = !expandCommentsBottomSheet.value
                    if (expandCommentsBottomSheet.value) {
                        comments = getComments(postData.id) ?: emptyList()
                    }
                },
                shape = RoundedCornerShape(24.dp)
            )

            if (compactPost && userRelation == EUserRelation.USER) {
                Spacer(modifier = Modifier.width(8.dp))

                AssistChip(
                    border = AssistChipDefaults.assistChipBorder(
                        borderWidth = 0.dp
                    ),
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        labelColor = MaterialTheme.colorScheme.onError
                    ),
                    label = {
                        Text(
                            style = MaterialTheme.typography.labelMedium,
                            text = stringResource(R.string.txt_feed_delete)
                        )
                    },
                    modifier = Modifier.padding(0.dp),
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(24.dp)
                )
            }
        }

        if (postData.photoUrls.isNotEmpty()) {
            postData.description?.let {
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = when (fullScreen) {
                        true -> Int.MAX_VALUE
                        else -> 2
                    },
                    modifier = Modifier
                        .clickable {
                            navigator.navigateArgs(
                                "${Screen.Post.value}/${user.username}/${postData.id}"
                            )
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .then(
                            when (fullScreen) {
                                true ->
                                    Modifier.padding(bottom = 24.dp)

                                else -> Modifier
                            }
                        ),
                    overflow = when (fullScreen) {
                        true -> TextOverflow.Visible
                        else -> TextOverflow.Ellipsis
                    },
                    style = MaterialTheme.typography.bodySmall,
                    text = it
                )
            }
        }
    }

    CommentsBottomSheet(
        comments = comments,
        open = expandCommentsBottomSheet,
        sendComment = { commentMessage ->
            sendComment(
                postData.id,
                commentMessage
            )
        }
    )
}

@Composable
fun CommentsBottomSheet(
    comments: List<Comment>,
    open: MutableState<Boolean>,
    sendComment: (commentMessage: String) -> Unit
) {
    var openProfileImageDialog by remember { mutableStateOf(false) }
    var userCommentProfilePicture by remember { mutableStateOf<String?>("") }
    var userCommentXp by remember { mutableIntStateOf(0) }
    var commentText by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val bottomSheetHeight = LocalConfiguration.current.screenHeightDp * 0.7
    val expandedProfilePictureSize = LocalConfiguration.current.screenWidthDp * 0.8

    BottomSheet(
        mode = BottomSheetModes.MODAL,
        open = open,
        skipPartiallyExpanded = true,
        title = stringResource(R.string.txt_profile_sheet_comments),
        content = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .height(bottomSheetHeight.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .weight(1f)
                ) {
                    items(comments) {
                        Row(
                            modifier = Modifier.padding(vertical = 16.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Image(
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .border(
                                        brush = XPUtils.getBorder(it.xp),
                                        shape = CircleShape,
                                        width = 2.dp
                                    )
                                    .clip(CircleShape)
                                    .clickable {
                                        userCommentProfilePicture = it.profilePicture
                                        userCommentXp = it.xp
                                        openProfileImageDialog = true
                                    }
                                    .height(48.dp)
                                    .width(48.dp),
                                painter = rememberAsyncImagePainter(
                                    model = it.profilePicture
                                )
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Row {
                                    Text(
                                        color = MaterialTheme.colorScheme.primary,
                                        style = MaterialTheme.typography.bodySmall,
                                        text = "${it.firstName} ${it.lastName}"
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        color = MaterialTheme.colorScheme.tertiary,
                                        style = MaterialTheme.typography.labelSmall,
                                        text = "@${it.username}"
                                    )
                                }
                                Text(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.bodySmall,
                                    text = it.commentMessage
                                )
                            }
                        }

                        if (openProfileImageDialog) {
                            Dialog(onDismissRequest = { openProfileImageDialog = false }) {
                                Image(
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .border(
                                            brush = XPUtils.getBorder(userCommentXp),
                                            shape = CircleShape,
                                            width = 2.dp
                                        )
                                        .clip(CircleShape)
                                        .height(expandedProfilePictureSize.dp)
                                        .width(expandedProfilePictureSize.dp),
                                    painter = rememberAsyncImagePainter(
                                        model = userCommentProfilePicture
                                    )
                                )
                            }
                        }
                    }
                }

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
                        onValueChange = { commentText = it },
                        value = commentText
                    )
                    IconButton(
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp),
                        onClick = {
                            focusManager.clearFocus()
                            sendComment(commentText)
                            commentText = ""
                        }
                    ) {
                        Icon(
                            contentDescription = stringResource(Icon.Send.description),
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp),
                            painter = painterResource(Icon.Send.value),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    )
}
