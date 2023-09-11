package com.shapeup.ui.screens.logged

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.ui.components.Carousel
import com.shapeup.ui.components.EPageButtons
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.FormFieldType
import com.shapeup.ui.components.Navbar
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator

@Preview
@Composable
fun FeedPreview() {
    ShapeUpTheme {
        FeedScreen(
            navigator = Navigator()
        )
    }
}

@Composable
fun FeedScreen(navigator: Navigator) {
    val focusManager = LocalFocusManager.current
    val data = listOf(
        "https://placehold.co/600x400.jpg",
        "https://placehold.co/1200x1400.jpg"
    )
    val posts = listOf(
        "Georgia John",
        "Mikael Sam"
    )

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
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
                onClick = { navigator.navigate(Screen.Feed) }
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
                label = "Search",
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
            posts.map {
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
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(
                                contentDescription = stringResource(Icon.Groups.description),
                                modifier = Modifier
                                    .height(32.dp)
                                    .width(32.dp),
                                painter = painterResource(Icon.Groups.value),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 1,
                            modifier = Modifier.weight(1f),
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodySmall,
                            text = it
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

                    Carousel(data = data)

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
                                contentDescription = stringResource(Icon.HeartFilled.description),
                                modifier = Modifier
                                    .height(24.dp)
                                    .width(24.dp),
                                painter = painterResource(Icon.HeartFilled.value),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        Text(
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 1,
                            modifier = Modifier.weight(1f),
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.labelMedium,
                            text = "25 likes"
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
                                    text = "129 comments"
                                )
                            },
                            modifier = Modifier.padding(0.dp),
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(24.dp)
                        )
                    }

                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall,
                        text = "Lorem Ipsum is simply dummy text of the printing and typesetting " +
                            "industry. Lorem Ipsum has been the industry Lorem"
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Divider(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 80.dp),
                    thickness = 1.dp
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Navbar(
            activePage = EPageButtons.HOME,
            navigator = navigator
        )
    }
}
