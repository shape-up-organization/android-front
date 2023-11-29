package com.shapeup.ui.navigation.routes.auth.composables

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.ui.screens.auth.SignInScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.navigator
import com.shapeup.ui.utils.helpers.viewModel
import com.shapeup.ui.viewModels.auth.AuthData
import com.shapeup.ui.viewModels.auth.AuthViewModel

fun NavGraphBuilder.screenSignIn(navController: NavHostController) {
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
        val authViewModel = it.viewModel<AuthViewModel>(navController)

        SignInScreen(
            data = AuthData(
                birth = authViewModel.birth,
                cellPhone = authViewModel.cellPhone,
                email = authViewModel.email,
                name = authViewModel.name,
                lastName = authViewModel.lastName,
                password = authViewModel.password,
                passwordConfirmation = authViewModel.passwordConfirmation,
                username = authViewModel.username,
                code = authViewModel.code
            ),
            handlers = authViewModel.handlers,
            navigator = navController.navigator
        )
    }
}
