package com.shapeup.ui.navigation.routes.logged.composables

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.ui.screens.logged.PostFilesScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.navigator
import com.shapeup.ui.utils.helpers.viewModel
import com.shapeup.ui.viewModels.logged.PostsViewModel

fun NavGraphBuilder.screenPostFiles(navController: NavHostController) {
    composable(
        route = Screen.PostFiles.value,
        enterTransition = { EnterTransition.None },
        popEnterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        val postsViewModel = it.viewModel<PostsViewModel>(navController)

        PostFilesScreen(
            postsHandlers = postsViewModel.handlers,
            navigator = navController.navigator
        )
    }
}
