package com.shapeup.ui.navigation.routes.logged

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.navigation.routes.logged.composables.settings.screenAccountCenter
import com.shapeup.ui.navigation.routes.logged.composables.settings.screenChangeAddress
import com.shapeup.ui.navigation.routes.logged.composables.settings.screenChangeBirthday
import com.shapeup.ui.navigation.routes.logged.composables.settings.screenChangeEmail
import com.shapeup.ui.navigation.routes.logged.composables.settings.screenChangeNumber
import com.shapeup.ui.navigation.routes.logged.composables.settings.screenChangePasswordSettings
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
import com.shapeup.ui.navigation.routes.logged.composables.screenTrainings
import com.shapeup.ui.navigation.routes.logged.composables.screenRank
import com.shapeup.ui.navigation.routes.logged.composables.settings.screenSettings
import com.shapeup.ui.utils.constants.Route
import com.shapeup.ui.utils.constants.Screen
import screenSearch

fun NavGraphBuilder.routeLogged(
    navController: NavHostController,
    sharedData: SharedData
) {
    navigation(
        route = Route.Logged.value,
        startDestination = Screen.Feed.value
    ) {
        screenFeed(
            navController,
            sharedData
        )

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
