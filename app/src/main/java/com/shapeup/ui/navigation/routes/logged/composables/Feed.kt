package com.shapeup.ui.navigation.routes.logged.composables

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.ui.screens.logged.FeedScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.navigator
import com.shapeup.ui.utils.helpers.viewModel
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyViewModel

fun NavGraphBuilder.screenFeed(navController: NavHostController) {
    composable(
        route = Screen.Feed.value,
        enterTransition = {
            EnterTransition.None
        },
        popEnterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        },
        popExitTransition = {
            ExitTransition.None
        }
    ) {
        val viewModel = it.viewModel<JourneyViewModel>(navController)
        viewModel.navigator = navController.navigator

        FeedScreen(
            data = JourneyData(
                friends = viewModel.friends,
                selectedUser = viewModel.selectedUser,
                userData = viewModel.userData
            ),
            handlers = viewModel.handlers,
            navigator = navController.navigator
        )
    }
}
