package com.shapeup.ui.screens.logged

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shapeup.R
import com.shapeup.api.services.users.RankType
import com.shapeup.api.services.users.UserRank
import com.shapeup.ui.components.EPageButtons
import com.shapeup.ui.components.ExpandableContent
import com.shapeup.ui.components.Navbar
import com.shapeup.ui.components.SnackbarHelper
import com.shapeup.ui.components.SnackbarType
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.utils.helpers.XPUtils
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyHandlers
import com.shapeup.ui.viewModels.logged.journeyDataMock
import com.shapeup.ui.viewModels.logged.journeyHandlersMock
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch

@Preview
@Composable
fun RankPreview() {
    ShapeUpTheme {
        RankScreen(
            journeyData = journeyDataMock,
            journeyHandlers = journeyHandlersMock,
            navigator = Navigator()
        )
    }
}

@Composable
fun RankScreen(
    journeyData: JourneyData,
    journeyHandlers: JourneyHandlers,
    navigator: Navigator
) {
    val titles = listOf(R.string.txt_rank_tab_global_title, R.string.txt_rank_tab_friend_title)

    var loadingRank by remember { mutableStateOf(true) }
    var tabSelected by remember { mutableIntStateOf(0) }
    var ranks by remember { mutableStateOf<List<UserRank>>(emptyList()) }
    var openSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    val refreshState = rememberSwipeRefreshState(isRefreshing = loadingRank)
    val coroutine = rememberCoroutineScope()

    val context = LocalContext.current

    fun getRank(type: RankType) {
        coroutine.launch {
            loadingRank = true
            val response = journeyHandlers.getRank(type)
            loadingRank = false

            when (response.status) {
                HttpStatusCode.OK -> ranks = response.data ?: emptyList()

                else -> {
                    openSnackbar = true
                    snackbarMessage = context.getString(R.string.txt_errors_generic)
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        getRank(RankType.GLOBAL)
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                    text = stringResource(R.string.txt_rank_header_title)
                )
            }

            TabRow(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                selectedTabIndex = tabSelected
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        onClick = {
                            tabSelected = index

                            when (tabSelected) {
                                0 -> getRank(RankType.GLOBAL)
                                1 -> getRank(RankType.FRIENDS)
                                else -> getRank(RankType.GLOBAL)
                            }
                        },
                        selected = tabSelected == index,
                        text = {
                            Text(
                                text = stringResource(title),
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }

            SwipeRefresh(
                state = refreshState,
                modifier = Modifier.weight(1f),
                onRefresh = {
                    coroutine.launch {
                        when (tabSelected) {
                            0 -> getRank(RankType.GLOBAL)
                            1 -> getRank(RankType.FRIENDS)
                            else -> getRank(RankType.GLOBAL)
                        }
                    }
                },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.primary,
                    )
                }
            ) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    item {
                        ExpandableContent(
                            visible = !loadingRank && ranks.isEmpty(),
                            content = {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = stringResource(R.string.txt_rank_error))
                                }
                            })
                    }
                    itemsIndexed(ranks) { index, it ->
                        Spacer(
                            modifier = Modifier
                                .height(16.dp)
                                .background(color = Color.Blue)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.primaryContainer)
                                .padding(24.dp, 8.dp)
                                .clickable {
                                    navigator.navigateArgs("${Screen.Profile.value}/${it.username}")
                                },
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyMedium,
                                text = "${index + 1}º"
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .width(220.dp)
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
                                        .background(XPUtils.getBorder(it.xp))
                                        .height(48.dp)
                                        .width(48.dp),
                                    painter = rememberAsyncImagePainter(
                                        model = it.profilePicture
                                    )
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.labelMedium,
                                    text = "${it.firstName} ${it.lastName}",
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 2
                                )
                            }

                            Text(
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.labelMedium,
                                text = "${it.xp}xp"
                            )
                        }
                    }
                }
            }
        }

        Navbar(
            activePage = EPageButtons.RANK,
            data = journeyData,
            navigator = navigator
        )
    }

    SnackbarHelper(
        message = snackbarMessage,
        open = openSnackbar,
        openSnackbar = { openSnackbar = it },
        type = SnackbarType.ERROR
    )
}
