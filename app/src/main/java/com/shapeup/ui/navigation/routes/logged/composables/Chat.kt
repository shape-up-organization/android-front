package com.shapeup.ui.navigation.routes.logged.composables

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.screens.logged.ChatScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.constants.TRANSITION
import com.shapeup.ui.utils.helpers.navigator
import com.shapeup.ui.utils.helpers.viewModel
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyViewModel

fun NavGraphBuilder.screenChat(
    navController: NavHostController,
    sharedData: SharedData
) {
    composable(
        route = "${Screen.Chat.value}/{username}",
        enterTransition = { fadeIn(animationSpec = tween(TRANSITION)) },
        popEnterTransition = { fadeIn(animationSpec = tween(TRANSITION)) },
        exitTransition = { fadeOut(animationSpec = tween(TRANSITION)) },
        popExitTransition = { fadeOut(animationSpec = tween(TRANSITION)) }
    ) {
        val username = it.arguments?.getString("username")

        if (username != null) {
            val journeyViewModel = it.viewModel<JourneyViewModel>(navController)
            journeyViewModel.sharedData = sharedData
            val userData = journeyViewModel.friends.value.find { friend ->
                friend.user.username == username
            }

            if (userData != null) {
                ChatScreen(
                    journeyData = JourneyData(
                        initialLoad = journeyViewModel.initialLoad,
                        friends = journeyViewModel.friends,
                        userData = journeyViewModel.userData
                    ),
                    journeyHandlers = journeyViewModel.handlers,
                    navigator = navController.navigator,
                    username = username
                )
            }
        }
    }
}
