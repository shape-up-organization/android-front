package com.shapeup.ui.screens.logged

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.components.Header
import com.shapeup.ui.components.NotificationRow
import com.shapeup.ui.components.TextListComponent
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.utils.helpers.TextHelper

@Preview
@Composable
fun NotificationScreenPreview() {
    ShapeUpTheme {
        NotificationScreen(navigator = Navigator())
    }
}

@Composable
fun NotificationScreen(
    navigator: Navigator
) {
    BackHandler {
        navigator.navigateBack()
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header(
            navigateTo = { navigator.navigateBack() },
            text = stringResource(R.string.notification_header_title)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(all = 24.dp)
            ) {
                val headTitleToFriendshipRequest = TextHelper(
                    text = stringResource(
                        R.string.txt_notification_head_title_to_friendship_request
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                val subtitleToFriendshipRequest = TextHelper(
                    text = stringResource(
                        R.string.txt_notification_subtitle_to_friendship_request
                    )
                )

                val textsToFriendshipRequests = listOf(
                    headTitleToFriendshipRequest,
                    subtitleToFriendshipRequest
                )

                TextListComponent(texts = textsToFriendshipRequests)

                IconButton(onClick = { navigator.navigate(Screen.FriendshipRequests) }) {
                    Icon(
                        contentDescription = stringResource(R.string.icon_arrow_forward),
                        painter = painterResource(id = Icon.ArrowForward.value),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(top = 32.dp)
        ) {
            repeat(30) {
                NotificationRow(
                    "Mayna Rose",
                    R.string.txt_notification_liked_your_picture
                )
            }
        }
    }
}
