package com.shapeup.ui.navigation.routes.auth.composables

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.ui.screens.auth.signUp.SignUpScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.navigator
import com.shapeup.ui.utils.helpers.viewModel
import com.shapeup.ui.viewModels.auth.SignUpFormData
import com.shapeup.ui.viewModels.auth.SignUpViewModel

fun NavGraphBuilder.screenSignUp(navController: NavHostController) {
    composable(
        route = Screen.SignUp.value,
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
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
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
        val viewModel = it.viewModel<SignUpViewModel>(navController)
        viewModel.navigator = navController.navigator

        SignUpScreen(
            activeStep = viewModel.activeStep,
            data = SignUpFormData(
                birthday = viewModel.birthday,
                email = viewModel.email,
                firstName = viewModel.firstName,
                lastName = viewModel.lastName,
                password = viewModel.password,
                passwordConfirmation = viewModel.passwordConfirmation,
                phone = viewModel.phone,
                username = viewModel.username,
                verificationCode = viewModel.verificationCode
            ),
            handlers = viewModel.handlers,
            navigator = navController.navigator
        )
    }
}
