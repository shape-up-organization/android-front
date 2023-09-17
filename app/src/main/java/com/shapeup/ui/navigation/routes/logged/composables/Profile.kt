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
        journeyViewModel.navigator = navController.navigator

        val userName = it.arguments?.getString("userName")
        val user = userName?.let { itUserName -> journeyViewModel.handlers.getUser(itUserName) }

        if (user != null) {
            val postsViewModel = it.viewModel<PostsViewModel>(navController)
            postsViewModel.navigator = navController.navigator
            postsViewModel.handlers.getUserPosts(user.username)

            ProfileScreen(
                journeyData = JourneyData(
                    friends = journeyViewModel.friends,
                    userData = journeyViewModel.userData
                ),
                journeyHandlers = journeyViewModel.handlers,
                postsData = PostsData(
                    posts = postsViewModel.posts,
                    specificPosts = postsViewModel.specificPosts
                ),
                navigator = navController.navigator,
                user = user
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
        journeyViewModel.navigator = navController.navigator

        val postsViewModel = it.viewModel<PostsViewModel>(navController)
        postsViewModel.navigator = navController.navigator
        postsViewModel.handlers.getUserPosts(journeyViewModel.userData.value.username)

        ProfileScreen(
            journeyData = JourneyData(
                friends = journeyViewModel.friends,
                userData = journeyViewModel.userData
            ),
            journeyHandlers = journeyViewModel.handlers,
            postsData = PostsData(
                posts = postsViewModel.posts,
                specificPosts = postsViewModel.specificPosts
            ),
            navigator = navController.navigator,
            user = journeyViewModel.userData.value
        )
    }
}
