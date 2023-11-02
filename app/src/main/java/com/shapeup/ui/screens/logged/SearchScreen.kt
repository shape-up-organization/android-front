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
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.shapeup.R
import com.shapeup.ui.components.ExpandableContent
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.FormFieldType
import com.shapeup.ui.components.Loading
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.utils.helpers.XPUtils
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyHandlers
import com.shapeup.ui.viewModels.logged.UserSearch
import com.shapeup.ui.viewModels.logged.journeyDataMock
import com.shapeup.ui.viewModels.logged.journeyHandlersMock
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch

@Preview
@Composable
fun SearchPreview() {
    ShapeUpTheme {
        SearchScreen(
            journeyData = journeyDataMock,
            journeyHandlers = journeyHandlersMock,
            navigator = Navigator()
        )
    }
}

@Composable
fun SearchScreen(
    journeyData: JourneyData,
    journeyHandlers: JourneyHandlers,
    navigator: Navigator
) {
    var loadingUsers by remember { mutableStateOf(false) }
    var searchedName by remember { mutableStateOf("") }
    var usersSearched by remember { mutableStateOf<List<UserSearch>>(emptyList()) }

    val coroutine = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current

    fun searchUser() {
        if (searchedName.isBlank() || searchedName == "@") {
            usersSearched = emptyList()
            return
        }

        coroutine.launch {
            loadingUsers = true
            val response = journeyHandlers.searchUsers(searchedName)
            loadingUsers = false

            when (response.status) {
                HttpStatusCode.OK -> usersSearched = response.data ?: emptyList()
            }
        }
    }

    BackHandler {
        navigator.navigateBack()
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween
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
                label = stringResource(R.string.txt_search_input),
                modifier = Modifier.weight(1f),
                type = FormFieldType.SEARCH,
                onValueChange = {
                    searchedName = it
                    searchUser()
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
                    visible = loadingUsers,
                    content = {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Loading()
                        }
                    })
            }
            item {
                ExpandableContent(
                    visible = !loadingUsers &&
                            usersSearched.isEmpty() &&
                            searchedName != "@" &&
                            searchedName.isNotBlank(),
                    content = {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = stringResource(R.string.txt_search_not_found))
                        }
                    })
            }
            itemsIndexed(usersSearched) { index, it ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .clickable {
                            navigator.navigateArgs("${Screen.Profile.value}/${it.username}")
                        }
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
                                brush = XPUtils.getBorder(it.xp),
                                shape = CircleShape,
                                width = 2.dp
                            )
                            .background(brush = XPUtils.getBorder(it.xp))
                            .height(48.dp)
                            .width(48.dp),
                        painter = rememberAsyncImagePainter(it.profilePicture)
                    )
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (it.friendshipStatus?.isFriend == true) {
                            Text(
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodySmall,
                                text = stringResource(R.string.txt_is_friend)
                            )
                        }

                        Text(
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodySmall,
                            text = "${it.firstName} ${it.lastName}"
                        )

                        Text(
                            color = MaterialTheme.colorScheme.tertiary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.labelSmall,
                            text = it.username
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .size(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            color = MaterialTheme.colorScheme.background,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodySmall,
                            text = "${it.xp}"
                        )
                    }
                }

                if (index != usersSearched.size - 1) {
                    Divider(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .padding(horizontal = 40.dp)
                    )
                }
            }
        }
    }
}
