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
import com.shapeup.ui.theme.GreenLight
import com.shapeup.ui.theme.RedError
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.helpers.Navigator

@Preview
@Composable
fun FriendshipRequestsScreenPreview() {
    ShapeUpTheme {
        FriendshipRequestsScreen(navigator = Navigator())
    }
}

@Composable
fun FriendshipRequestsScreen(
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
            text = stringResource(id = R.string.requests_header_title)
        )

        repeat(5) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    NotificationRow(
                        userFullName = "Brian Cozunet British",
                        action = R.string.sent_you_friendship_request
                    )

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            contentDescription = stringResource(R.string.icon_accept),
                            painter = painterResource(Icon.Accept.value),
                            tint = GreenLight
                        )
                    }

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            contentDescription = stringResource(R.string.icon_decline),
                            painter = painterResource(Icon.Decline.value),
                            tint = RedError
                        )
                    }
                }
            }
        }
    }
}
