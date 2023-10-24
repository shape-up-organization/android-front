package com.shapeup.ui.navigation.routes.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.navigation.routes.auth.composables.screenAccountVerification
import com.shapeup.ui.navigation.routes.auth.composables.screenSignIn
import com.shapeup.ui.navigation.routes.auth.composables.screenSignUp
import com.shapeup.ui.navigation.routes.auth.composables.screenWelcome
import com.shapeup.ui.navigation.routes.auth.routes.forgotPassword.routeForgotPassword
import com.shapeup.ui.utils.constants.Route
import com.shapeup.ui.utils.constants.Screen

fun NavGraphBuilder.routeAuth(
    navController: NavHostController,
    sharedData: SharedData
) {
    navigation(
        route = Route.Auth.value,
        startDestination = Screen.Welcome.value
    ) {
        screenWelcome(navController)
        screenSignIn(
            navController,
            sharedData
        )
        routeForgotPassword(navController)
        screenSignUp(
            navController,
            sharedData
        )
        screenAccountVerification(
            navController,
            sharedData
        )
    }
}
