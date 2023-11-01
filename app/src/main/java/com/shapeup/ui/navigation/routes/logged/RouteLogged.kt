package com.shapeup.ui.navigation.routes.logged

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.shapeup.ui.navigation.routes.logged.composables.screenAccountCenter
import com.shapeup.ui.navigation.routes.logged.composables.screenChangeAddress
import com.shapeup.ui.navigation.routes.logged.composables.screenChangeBirthday
import com.shapeup.ui.navigation.routes.logged.composables.screenChangeEmail
import com.shapeup.ui.navigation.routes.logged.composables.screenChangeNumber
import com.shapeup.ui.navigation.routes.logged.composables.screenChangePasswordSettings
import com.shapeup.ui.navigation.routes.logged.composables.screenChat
import com.shapeup.ui.navigation.routes.logged.composables.screenChatsList
import com.shapeup.ui.navigation.routes.logged.composables.screenEditProfile
import com.shapeup.ui.navigation.routes.logged.composables.screenFeed
import com.shapeup.ui.navigation.routes.logged.composables.screenFriendshipRequest
import com.shapeup.ui.navigation.routes.logged.composables.screenNotification
import com.shapeup.ui.navigation.routes.logged.composables.screenPost
import com.shapeup.ui.navigation.routes.logged.composables.screenPostFiles
import com.shapeup.ui.navigation.routes.logged.composables.screenPostText
import com.shapeup.ui.navigation.routes.logged.composables.screenProfile
import com.shapeup.ui.navigation.routes.logged.composables.screenRank
import com.shapeup.ui.navigation.routes.logged.composables.screenSettings
import com.shapeup.ui.navigation.routes.logged.composables.screenTrainings
import com.shapeup.ui.utils.constants.Route
import com.shapeup.ui.utils.constants.Screen
import screenSearch

fun NavGraphBuilder.routeLogged(navController: NavHostController) {
    navigation(
        route = Route.Logged.value,
        startDestination = Screen.Feed.value
    ) {
        screenFeed(navController)

        screenNotification(navController)

        screenChatsList(navController)

        screenChat(navController)

        screenFriendshipRequest(navController)

        screenPost(navController)

        screenPostText(navController)

        screenPostFiles(navController)

        screenProfile(navController)

        screenEditProfile(navController)

        screenTrainings(navController)

        screenRank(navController)

        screenSearch(navController)

        screenSettings(navController)

        screenAccountCenter(navController)

        screenChangeAddress(navController)

        screenChangeEmail(navController)

        screenChangeNumber(navController)

        screenChangePasswordSettings(navController)

        screenChangeBirthday(navController)
    }
}
