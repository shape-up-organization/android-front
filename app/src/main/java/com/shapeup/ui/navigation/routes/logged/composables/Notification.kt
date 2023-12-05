package com.shapeup.ui.navigation.routes.logged.composables

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.ui.screens.logged.NotificationScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.constants.TRANSITION
import com.shapeup.ui.utils.helpers.navigator

fun NavGraphBuilder.screenNotification(navController: NavHostController) {
    composable(
        route = Screen.Notification.value,
        enterTransition = { fadeIn(animationSpec = tween(TRANSITION)) },
        popEnterTransition = { fadeIn(animationSpec = tween(TRANSITION)) },
        exitTransition = { fadeOut(animationSpec = tween(TRANSITION)) },
        popExitTransition = { fadeOut(animationSpec = tween(TRANSITION)) }
    ) {
        NotificationScreen(
            navigator = navController.navigator
        )
    }
}
