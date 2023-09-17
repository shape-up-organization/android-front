package com.shapeup.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.shapeup.R
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

        IconButton(
            modifier = Modifier
                .offset(x = 0.dp, y = (-12).dp)
                .height(64.dp)
                .width(64.dp),
            onClick = { navigator.navigate(EPageButtons.ADD.screen) }
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
                    .height(64.dp)
                    .width(64.dp),
                painter = painterResource(EPageButtons.ADD.icon.value)
            )
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
                painter = rememberAsyncImagePainter("https://picsum.photos/id/64/4326/2884")
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
        screen = Screen.Feed
    ),
    TRAININGS(
        description = R.string.icon_fitness_center,
        icon = Icon.FitnessCenter,
        screen = Screen.Feed
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
