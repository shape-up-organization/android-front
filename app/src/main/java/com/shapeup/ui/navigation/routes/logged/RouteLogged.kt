package com.shapeup.ui.navigation.routes.logged

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.shapeup.ui.navigation.routes.logged.composables.screenFeed
import com.shapeup.ui.navigation.routes.logged.composables.screenFriendshipRequest
import com.shapeup.ui.navigation.routes.logged.composables.screenNotification
import com.shapeup.ui.navigation.routes.logged.composables.screenProfile
import com.shapeup.ui.utils.constants.Route
import com.shapeup.ui.utils.constants.Screen

fun NavGraphBuilder.routeLogged(navController: NavHostController) {
    navigation(
        route = Route.Logged.value,
        startDestination = Screen.Feed.value
    ) {
        screenFeed(navController)

        screenNotification(navController)

        screenFriendshipRequest(navController)

        screenProfile(navController)
    }
}
