package com.shapeup.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.shapeup.R
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.utils.helpers.XPUtils
import com.shapeup.ui.viewModels.logged.Post
import com.shapeup.ui.viewModels.logged.User

@Composable
fun CardPost(
    fullScreen: Boolean = false,
    postData: Post,
    navigator: Navigator,
    user: User
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                when (fullScreen) {
                    true ->
                        Modifier
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState())

                    else -> Modifier
                }
            )
    ) {
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
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(24.dp)
            )
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
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .then(when (fullScreen) {
                            true ->
                                Modifier.padding(bottom = 24.dp)

                            else -> Modifier
                        }),
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
}
