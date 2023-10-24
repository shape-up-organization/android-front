package com.shapeup.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.navigation.routes.auth.routeAuth
import com.shapeup.ui.navigation.routes.logged.routeLogged
import com.shapeup.ui.screens.SplashScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.navigator
import com.shapeup.ui.utils.helpers.viewModel
import com.shapeup.ui.viewModels.auth.SignInViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    sharedData: SharedData
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.value
    ) {
        composable(
            route = Screen.Splash.value,
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(600)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(600)
                )
            }
        ) {
            val signInViewModel = it.viewModel<SignInViewModel>(navController)
            signInViewModel.navigator = navController.navigator
            signInViewModel.sharedData = sharedData

            SplashScreen(
                handlers = signInViewModel.handlers,
                navigator = navController.navigator
            )
        }

        routeAuth(
            navController,
            sharedData
        )

        routeLogged(
            navController,
            sharedData
        )
    }
}
