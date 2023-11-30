package com.shapeup.ui.navigation.routes.logged.composables

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.ui.screens.logged.ProfileScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.navigator
import com.shapeup.ui.utils.helpers.viewModel
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyViewModel
import com.shapeup.ui.viewModels.logged.PostsData
import com.shapeup.ui.viewModels.logged.PostsViewModel

fun NavGraphBuilder.screenProfile(navController: NavHostController) {
    composable(
        route = "${Screen.Profile.value}/{userName}",
        enterTransition = { EnterTransition.None },
        popEnterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        val journeyViewModel = it.viewModel<JourneyViewModel>(navController)

        val username = it.arguments?.getString("userName")

        if (username != null) {
            val postsViewModel = it.viewModel<PostsViewModel>(navController)

            ProfileScreen(
                journeyData = JourneyData(
                    initialLoad = journeyViewModel.initialLoad,
                    friends = journeyViewModel.friends,
                    userData = journeyViewModel.userData
                ),
                journeyHandlers = journeyViewModel.handlers,
                postsData = PostsData(
                    posts = postsViewModel.posts,
                    specificPosts = postsViewModel.specificPosts
                ),
                postsHandlers = postsViewModel.handlers,
                navigator = navController.navigator,
                username = username
            )
        }
    }

    composable(
        route = Screen.Profile.value,
        enterTransition = { EnterTransition.None },
        popEnterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        val journeyViewModel = it.viewModel<JourneyViewModel>(navController)
        val postsViewModel = it.viewModel<PostsViewModel>(navController)

        ProfileScreen(
            journeyData = JourneyData(
                initialLoad = journeyViewModel.initialLoad,
                friends = journeyViewModel.friends,
                userData = journeyViewModel.userData
            ),
            journeyHandlers = journeyViewModel.handlers,
            postsData = PostsData(
                posts = postsViewModel.posts,
                specificPosts = postsViewModel.specificPosts
            ),
            postsHandlers = postsViewModel.handlers,
            navigator = navController.navigator,
            username = journeyViewModel.userData.value.username
        )
    }
}
