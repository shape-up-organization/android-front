package com.shapeup.ui.navigation.routes.logged.composables.settings

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.ui.screens.logged.settings.SettingsScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.navigator

fun NavGraphBuilder.screenSettings(navController: NavHostController) {
    composable(
        route = Screen.Settings.value,
        enterTransition = { EnterTransition.None },
        popEnterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        SettingsScreen(
            navigator = navController.navigator
        )
    }
}