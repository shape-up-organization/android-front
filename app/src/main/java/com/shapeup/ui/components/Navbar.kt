package com.shapeup.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator

@Composable
fun Navbar(
    activePage: EPageButtons,
    navigator: Navigator
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
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

        PageButton(
            activePage = activePage,
            isBigger = true,
            navigator = navigator,
            pageButton = EPageButtons.ADD
        )

        PageButton(
            activePage = activePage,
            navigator = navigator,
            pageButton = EPageButtons.TRAININGS
        )

        PageButton(
            activePage = activePage,
            navigator = navigator,
            pageButton = EPageButtons.PROFILE
        )
    }
}

@Composable
fun PageButton(
    activePage: EPageButtons,
    isBigger: Boolean? = false,
    navigator: Navigator,
    pageButton: EPageButtons
) {
    val iconButtonModifier = when (isBigger) {
        true ->
            Modifier
                .offset(x = 0.dp, y = (-12).dp)
                .height(64.dp)
                .width(64.dp)

        else -> Modifier
    }

    val iconModifier = when (isBigger) {
        true ->
            Modifier
                .height(64.dp)
                .width(64.dp)

        else ->
            Modifier
                .height(32.dp)
                .width(32.dp)
    }

    IconButton(
        modifier = iconButtonModifier,
        onClick = { navigator.navigate(pageButton.screen) }
    ) {
        Icon(
            contentDescription = stringResource(pageButton.description),
            modifier = iconModifier,
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
        description = R.string.icon_groups,
        icon = Icon.Groups,
        screen = Screen.Feed
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
