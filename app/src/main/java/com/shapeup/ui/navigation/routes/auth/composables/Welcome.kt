package com.shapeup.ui.navigation.routes.auth.composables

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.screens.auth.WelcomeScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.navigator
import com.shapeup.ui.utils.helpers.viewModel
import com.shapeup.ui.viewModels.auth.AuthViewModel
import com.shapeup.ui.viewModels.logged.JourneyViewModel
import com.shapeup.ui.viewModels.logged.PostsViewModel

fun NavGraphBuilder.screenWelcome(navController: NavHostController, sharedData: SharedData) {
    composable(
        route = Screen.Welcome.value,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(600)
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
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(600)
            )
        }
    ) {
        val authViewModel = it.viewModel<AuthViewModel>(navController)
        authViewModel.navigator = navController.navigator
        authViewModel.sharedData = sharedData

        val journeyViewModel = it.viewModel<JourneyViewModel>(navController)
        journeyViewModel.sharedData = sharedData

        val postsViewModel = it.viewModel<PostsViewModel>(navController)
        postsViewModel.navigator = navController.navigator
        postsViewModel.sharedData = sharedData

        WelcomeScreen(navController.navigator)
    }
}
