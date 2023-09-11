package com.shapeup.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shapeup.ui.navigation.routes.auth.routeAuth
import com.shapeup.ui.navigation.routes.auth.routeLogged
import com.shapeup.ui.screens.SplashScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.navigator

@Composable
fun Navigation(navController: NavHostController) {
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
            SplashScreen(navController.navigator)
        }

        routeAuth(navController)

        routeLogged(navController)
    }
}
