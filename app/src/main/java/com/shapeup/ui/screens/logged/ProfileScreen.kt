package com.shapeup.ui.screens.logged

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import coil.compose.rememberAsyncImagePainter
import com.shapeup.R
import com.shapeup.ui.components.EPageButtons
import com.shapeup.ui.components.Navbar
import com.shapeup.ui.theme.LevelGradient
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.helpers.Navigator

@Preview
@Composable
fun ProfilePreview() {
    ShapeUpTheme {
        ProfileScreen(
            navigator = Navigator()
        )
    }
}

@Composable
fun ProfileScreen(navigator: Navigator) {
    var tabSelected by remember { mutableIntStateOf(0) }

    val imageSize = LocalConfiguration.current.screenWidthDp.dp / 3
    val titles = listOf("Photos", "Posts")
    val isUserProfile = false

    val userName = "John Will"
    val userLevel = 31
    val posts = listOf(
        "https://picsum.photos/id/42/3456/2304",
        "https://picsum.photos/id/59/2464/1632",
        "https://picsum.photos/id/56/2880/1920",
        "https://picsum.photos/id/57/2448/3264",
        "https://picsum.photos/id/49/1280/792",
        "https://picsum.photos/id/49/1280/792",
        "https://picsum.photos/id/54/3264/2176",
        "https://picsum.photos/id/57/2448/3264",
        "https://picsum.photos/id/64/4326/2884",
        "https://picsum.photos/id/73/5000/3333",
        "https://picsum.photos/id/76/4912/3264",
        "https://picsum.photos/id/75/1999/2998",
        "https://picsum.photos/id/42/3456/2304",
        "https://picsum.photos/id/59/2464/1632",
        "https://picsum.photos/id/56/2880/1920",
        "https://picsum.photos/id/57/2448/3264"
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
        if (!isUserProfile) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navigator.navigateBack() }) {
                    Icon(
                        contentDescription = stringResource(R.string.icon_arrow_back),
                        painter = painterResource(Icon.ArrowBack.value),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelLarge,
                    text = userName
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 24.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .border(
                                    brush = LevelGradient.LEVEL_1.value,
                                    shape = CircleShape,
                                    width = 2.dp
                                )
                                .clip(CircleShape)
                                .height(64.dp)
                                .width(64.dp),
                            painter = rememberAsyncImagePainter(
                                model = "https://picsum.photos/id/40/4106/2806"
                            )
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.bodyMedium,
                                text = userName
                            )
                            Text(
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.labelSmall,
                                text = "level $userLevel"
                            )
                        }
                    }

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
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.bodyMedium,
                                text = "Request"
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Icon(
                                contentDescription = stringResource(Icon.Plus.description),
                                modifier = Modifier
                                    .height(12.dp)
                                    .width(12.dp),
                                painter = painterResource(Icon.Plus.value),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        },
                        modifier = Modifier.padding(0.dp),
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(24.dp)
                    )
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

            LazyVerticalGrid(
                columns = GridCells.Fixed(3)
            ) {
                items(posts) {
                    Image(
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(imageSize),
                        painter = rememberAsyncImagePainter(model = it)
                    )
                }
            }
        }
        Navbar(
            activePage = EPageButtons.PROFILE,
            navigator = navigator
        )
    }
}
