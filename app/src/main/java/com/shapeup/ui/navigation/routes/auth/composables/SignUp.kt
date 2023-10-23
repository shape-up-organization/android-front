package com.shapeup.ui.navigation.routes.auth.composables

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.screens.auth.signUp.SignUpScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.navigator
import com.shapeup.ui.utils.helpers.viewModel
import com.shapeup.ui.viewModels.auth.SignUpFormData
import com.shapeup.ui.viewModels.auth.SignUpViewModel

fun NavGraphBuilder.screenSignUp(
    navController: NavHostController,
    sharedData: SharedData
) {
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
        viewModel.sharedData = sharedData

        SignUpScreen(
            activeStep = viewModel.activeStep,
            data = SignUpFormData(
                birth = viewModel.birth,
                cellPhone = viewModel.cellPhone,
                email = viewModel.email,
                name = viewModel.name,
                lastName = viewModel.lastName,
                password = viewModel.password,
                passwordConfirmation = viewModel.passwordConfirmation,
                username = viewModel.username,
                code = viewModel.code
            ),
            handlers = viewModel.handlers,
            navigator = navController.navigator
        )
    }
}
