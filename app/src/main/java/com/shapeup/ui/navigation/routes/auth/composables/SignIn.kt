package com.shapeup.ui.navigation.routes.auth.composables

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.screens.auth.SignInScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.navigator
import com.shapeup.ui.utils.helpers.viewModel
import com.shapeup.ui.viewModels.auth.SignInFormData
import com.shapeup.ui.viewModels.auth.SignInViewModel

fun NavGraphBuilder.screenSignIn(
    navController: NavHostController,
    sharedData: SharedData
) {
    composable(
        route = Screen.SignIn.value,
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
        val signInViewModel = it.viewModel<SignInViewModel>(navController)
        signInViewModel.navigator = navController.navigator
        signInViewModel.sharedData = sharedData

        SignInScreen(
            data = SignInFormData(
                email = signInViewModel.email,
                password = signInViewModel.password
            ),
            handlers = signInViewModel.handlers,
            navigator = navController.navigator
        )
    }
}
