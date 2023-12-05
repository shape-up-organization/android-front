package com.shapeup.ui.navigation.routes.logged.composables

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.ui.screens.logged.PostScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.constants.TRANSITION
import com.shapeup.ui.utils.helpers.navigator
import com.shapeup.ui.utils.helpers.viewModel
import com.shapeup.ui.viewModels.logged.JourneyViewModel
import com.shapeup.ui.viewModels.logged.PostsViewModel

fun NavGraphBuilder.screenPost(navController: NavHostController) {
    composable(
        route = "${Screen.Post.value}/{userName}/{postId}",
        enterTransition = { fadeIn(animationSpec = tween(TRANSITION)) },
        popEnterTransition = { fadeIn(animationSpec = tween(TRANSITION)) },
        exitTransition = { fadeOut(animationSpec = tween(TRANSITION)) },
        popExitTransition = { fadeOut(animationSpec = tween(TRANSITION)) }
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
