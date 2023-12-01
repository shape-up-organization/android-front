package com.shapeup.ui.navigation.routes.logged.composables

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.screens.logged.TrainingsScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.navigator
import com.shapeup.ui.utils.helpers.viewModel
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyViewModel
import com.shapeup.ui.viewModels.logged.TrainingsViewModel

fun NavGraphBuilder.screenTrainings(
    navController: NavHostController,
    sharedData: SharedData
) {
    composable(
        route = Screen.Trainings.value,
        enterTransition = { EnterTransition.None },
        popEnterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        val journeyViewModel = it.viewModel<JourneyViewModel>(navController)
        journeyViewModel.sharedData = sharedData

        val trainingViewModel = it.viewModel<TrainingsViewModel>(navController)
        trainingViewModel.sharedData = sharedData

        TrainingsScreen(
            journeyData = JourneyData(
                initialLoad = journeyViewModel.initialLoad,
                friends = journeyViewModel.friends,
                userData = journeyViewModel.userData
            ),
            navigator = navController.navigator,
            trainingsHandlers = trainingViewModel.handlers
        )
    }
}
