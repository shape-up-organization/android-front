package com.shapeup.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import coil.compose.rememberAsyncImagePainter
import com.shapeup.R
import com.shapeup.api.services.users.getUserDataMock
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.utils.helpers.XPUtils
import com.shapeup.ui.viewModels.logged.JourneyData

@Composable
fun Navbar(
    activePage: EPageButtons,
    data: JourneyData,
    navigator: Navigator
) {
    var openPostOptions by remember { mutableStateOf(false) }
    var enablePopupInteractions by remember { mutableStateOf(false) }

    val popupAlpha by updateTransition(targetState = openPostOptions, label = "")
        .animateFloat(
            transitionSpec = { tween(durationMillis = 300) },
            label = "popupAlpha"
        ) {
            if (it) 1f else 0f
        }

    LaunchedEffect(openPostOptions) {
        if (openPostOptions) {
            enablePopupInteractions = true
        }
    }

    LaunchedEffect(popupAlpha) {
        if (popupAlpha == 0f) {
            enablePopupInteractions = false
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 0.dp
            )
    ) {
        PageButton(
            activePage = activePage,
            navigator = navigator,
            pageButton = EPageButtons.HOME
        )

        PageButton(
            activePage = activePage,
            navigator = navigator,
            pageButton = EPageButtons.RANK
        )

        Box {
            IconButton(
                modifier = Modifier
                    .offset(x = 0.dp, y = (-12).dp)
                    .height(48.dp)
                    .width(48.dp),
                onClick = { openPostOptions = !openPostOptions }
            ) {
                Icon(
                    contentDescription = stringResource(EPageButtons.ADD.description),
                    modifier = Modifier
                        .graphicsLayer(alpha = 0.99f)
                        .drawWithCache {
                            onDrawWithContent {
                                drawContent()
                                drawRect(
                                    XPUtils.getBorder(data.userData.value.xp),
                                    blendMode = BlendMode.SrcAtop
                                )
                            }
                        }
                        .height(48.dp)
                        .width(48.dp),
                    painter = painterResource(EPageButtons.ADD.icon.value)
                )
            }

            if (enablePopupInteractions) {
                Popup(
                    offset = IntOffset(0, -320),
                    onDismissRequest = { openPostOptions = false }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .alpha(popupAlpha)
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .background(
                                MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(24.dp)
                    ) {
                        Button(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .weight(1f),
                            onClick = { navigator.navigate(Screen.PostFiles) }
                        ) {
                            Icon(
                                contentDescription = stringResource(Icon.Image.description),
                                painter = painterResource(Icon.Image.value),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.labelMedium,
                                text = stringResource(Icon.Image.description)
                            )
                        }
                        Button(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .weight(1f),
                            onClick = { navigator.navigate(Screen.PostText) }
                        ) {
                            Icon(
                                contentDescription = stringResource(Icon.Text.description),
                                painter = painterResource(Icon.Text.value),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.labelMedium,
                                text = stringResource(Icon.Text.description)
                            )
                        }
                    }
                }
            }
        }

        PageButton(
            activePage = activePage,
            navigator = navigator,
            pageButton = EPageButtons.TRAININGS
        )

        IconButton(
            modifier = Modifier
                .height(32.dp)
                .width(32.dp),
            onClick = { navigator.navigate(Screen.Profile) }
        ) {
            Image(
                contentDescription = stringResource(R.string.user_profile_pic),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .border(
                        brush = XPUtils.getBorder(data.userData.value.xp),
                        shape = CircleShape,
                        width = 2.dp
                    )
                    .clip(CircleShape)
                    .height(24.dp)
                    .width(24.dp),
                painter = rememberAsyncImagePainter(getUserDataMock.profilePicture)
            )
        }
    }
}

@Composable
fun PageButton(
    activePage: EPageButtons,
    navigator: Navigator,
    pageButton: EPageButtons
) {
    IconButton(
        modifier = Modifier
            .height(32.dp)
            .width(32.dp),
        onClick = { navigator.navigate(pageButton.screen) }
    ) {
        Icon(
            contentDescription = stringResource(pageButton.description),
            modifier = Modifier
                .height(24.dp)
                .width(24.dp),
            painter = painterResource(pageButton.icon.value),
            tint = EPageButtons.getColor(
                activePage = activePage,
                buttonPage = EPageButtons.valueOf(pageButton.name)
            )
        )
    }
}

enum class EPageButtons(
    val description: Int,
    val icon: Icon,
    val screen: Screen
) {
    ADD(
        description = R.string.icon_add,
        icon = Icon.Add,
        screen = Screen.Feed
    ),
    HOME(
        description = R.string.icon_home,
        icon = Icon.Home,
        screen = Screen.Feed
    ),
    PROFILE(
        description = R.string.user_profile_pic,
        icon = Icon.Groups,
        screen = Screen.Profile
    ),
    RANK(
        description = R.string.icon_rank,
        icon = Icon.Rank,
        screen = Screen.Rank
    ),
    TRAININGS(
        description = R.string.icon_fitness_center,
        icon = Icon.FitnessCenter,
        screen = Screen.Trainings
    );

    companion object {
        @Composable
        fun getColor(activePage: EPageButtons, buttonPage: EPageButtons): Color {
            return when (activePage) {
                buttonPage -> {
                    MaterialTheme.colorScheme.primary
                }

                else -> MaterialTheme.colorScheme.onBackground
            }
        }
    }
}
