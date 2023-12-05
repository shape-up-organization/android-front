import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shapeup.ui.screens.logged.SearchScreen
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.constants.TRANSITION
import com.shapeup.ui.utils.helpers.navigator
import com.shapeup.ui.utils.helpers.viewModel
import com.shapeup.ui.viewModels.logged.JourneyViewModel

fun NavGraphBuilder.screenSearch(navController: NavHostController) {
    composable(
        route = Screen.Search.value,
        enterTransition = { fadeIn(animationSpec = tween(TRANSITION)) },
        popEnterTransition = { fadeIn(animationSpec = tween(TRANSITION)) },
        exitTransition = { fadeOut(animationSpec = tween(TRANSITION)) },
        popExitTransition = { fadeOut(animationSpec = tween(TRANSITION)) }
    ) {
        val journeyViewModel = it.viewModel<JourneyViewModel>(navController)

        SearchScreen(
            journeyHandlers = journeyViewModel.handlers,
            navigator = navController.navigator
        )
    }
}
