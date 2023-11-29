package com.shapeup.ui.navigation.routes.logged.composables

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.ui.screens.logged.PostScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.navigator
import com.shapeup.ui.utils.helpers.viewModel
import com.shapeup.ui.viewModels.logged.JourneyViewModel
import com.shapeup.ui.viewModels.logged.PostsViewModel

fun NavGraphBuilder.screenPost(navController: NavHostController) {
    composable(
        route = "${Screen.Post.value}/{userName}/{postId}",
        enterTransition = { EnterTransition.None },
        popEnterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        val userName = it.arguments?.getString("userName")
        val postId = it.arguments?.getString("postId")

        if (userName != null && postId != null) {
            val journeyViewModel = it.viewModel<JourneyViewModel>(navController)
            val postsViewModel = it.viewModel<PostsViewModel>(navController)

            PostScreen(
                navigator = navController.navigator,
                postId = postId,
                postsHandlers = postsViewModel.handlers,
                journeyHandlers = journeyViewModel.handlers,
                username = userName,
            )
        }
    }
}
