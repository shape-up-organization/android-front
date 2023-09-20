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
        val journeyViewModel = it.viewModel<JourneyViewModel>(navController)
        journeyViewModel.navigator = navController.navigator

        val userName = it.arguments?.getString("userName")
        val user = userName?.let { itUserName -> journeyViewModel.handlers.getUser(itUserName) }

        if (user != null) {
            val postsViewModel = it.viewModel<PostsViewModel>(navController)
            postsViewModel.navigator = navController.navigator

            val postId = it.arguments?.getString("postId")
            val postData = postId?.let { itPostId -> postsViewModel.handlers.getPostById(itPostId) }

            if (postData != null) {
                PostScreen(
                    navigator = navController.navigator,
                    postData = postData,
                    user = user,
                    userRelation = journeyViewModel.handlers.getUserRelation(user.username)
                )
            }
        }
    }
}
