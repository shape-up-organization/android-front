package com.shapeup.ui.navigation.routes.auth.routes.forgotPassword.composables

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.ui.screens.auth.forgotPassword.ChangePasswordScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.navigator

fun NavGraphBuilder.screenChangePassword(navController: NavHostController) {
    composable(
        route = Screen.ChangePassword.value,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        }
    ) {
        ChangePasswordScreen(navController.navigator)
    }
}
