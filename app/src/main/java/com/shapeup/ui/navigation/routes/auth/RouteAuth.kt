package com.shapeup.ui.navigation.routes.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.shapeup.ui.navigation.routes.auth.composables.screenSignIn
import com.shapeup.ui.navigation.routes.auth.composables.screenSignUp
import com.shapeup.ui.navigation.routes.auth.composables.screenWelcome
import com.shapeup.ui.navigation.routes.auth.routes.forgotPassword.routeForgotPassword
import com.shapeup.ui.utils.constants.Route
import com.shapeup.ui.utils.constants.Screen

fun NavGraphBuilder.routeAuth(navController: NavHostController) {
    navigation(
        route = Route.Auth.value,
        startDestination = Screen.Welcome.value
    ) {
        screenWelcome(navController)
        screenSignIn(navController)
        routeForgotPassword(navController)
        screenSignUp(navController)
    }
}
