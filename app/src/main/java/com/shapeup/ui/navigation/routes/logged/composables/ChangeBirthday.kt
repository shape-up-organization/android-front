package com.shapeup.ui.navigation.routes.logged.composables

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.ui.screens.logged.ChangeBirthdayScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.navigator
import com.shapeup.ui.utils.helpers.viewModel
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyViewModel

fun NavGraphBuilder.screenChangeBirthday(navController: NavHostController) {
    composable(
        route = Screen.ChangeBirthday.value,
        enterTransition = { EnterTransition.None },
        popEnterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        val journeyViewModel = it.viewModel<JourneyViewModel>(navController)

        ChangeBirthdayScreen(
            journeyData = JourneyData(
                initialLoad = journeyViewModel.initialLoad,
                friends = journeyViewModel.friends,
                userData = journeyViewModel.userData
            ),
            journeyHandlers = journeyViewModel.handlers,
            navigator = navController.navigator
        )
    }
}