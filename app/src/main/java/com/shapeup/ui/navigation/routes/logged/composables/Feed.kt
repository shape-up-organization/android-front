package com.shapeup.ui.navigation.routes.logged.composables

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.screens.logged.FeedScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.constants.TRANSITION
import com.shapeup.ui.utils.helpers.navigator
import com.shapeup.ui.utils.helpers.viewModel
import com.shapeup.ui.viewModels.auth.AuthViewModel
import com.shapeup.ui.viewModels.logged.JourneyData
import com.shapeup.ui.viewModels.logged.JourneyViewModel
import com.shapeup.ui.viewModels.logged.PostsData
import com.shapeup.ui.viewModels.logged.PostsViewModel

fun NavGraphBuilder.screenFeed(
    navController: NavHostController,
    sharedData: SharedData
) {
    composable(
        route = Screen.Feed.value,
        enterTransition = { fadeIn(animationSpec = tween(TRANSITION)) },
        popEnterTransition = { fadeIn(animationSpec = tween(TRANSITION)) },
        exitTransition = { fadeOut(animationSpec = tween(TRANSITION)) },
        popExitTransition = { fadeOut(animationSpec = tween(TRANSITION)) }
    ) {
        val authViewModel = it.viewModel<AuthViewModel>(navController)
        authViewModel.navigator = navController.navigator
        authViewModel.sharedData = sharedData

        val journeyViewModel = it.viewModel<JourneyViewModel>(navController)
        journeyViewModel.sharedData = sharedData

        val postsViewModel = it.viewModel<PostsViewModel>(navController)
        postsViewModel.navigator = navController.navigator
        postsViewModel.sharedData = sharedData

        FeedScreen(
            authHandlers = authViewModel.handlers,
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
            navigator = navController.navigator
        )
    }
}
