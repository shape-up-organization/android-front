package com.shapeup.ui.navigation.routes.auth.composables

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.ui.screens.auth.signUp.AccountVerificationScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.navigator
import com.shapeup.ui.utils.helpers.viewModel
import com.shapeup.ui.viewModels.auth.AuthViewModel

fun NavGraphBuilder.screenAccountVerification(navController: NavHostController) {
    composable(
        route = Screen.AccountVerification.value,
        enterTransition = { EnterTransition.None },
        popEnterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        val authViewModel = it.viewModel<AuthViewModel>(navController)

        AccountVerificationScreen(
            code = authViewModel.code,
            email = authViewModel.email,
            handlers = authViewModel.handlers,
            navigator = navController.navigator
        )
    }
}
