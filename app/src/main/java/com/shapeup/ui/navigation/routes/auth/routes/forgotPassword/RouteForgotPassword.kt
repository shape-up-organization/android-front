package com.shapeup.ui.navigation.routes.auth.routes.forgotPassword

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.shapeup.ui.navigation.routes.auth.routes.forgotPassword.composables.screenChangePassword
import com.shapeup.ui.navigation.routes.auth.routes.forgotPassword.composables.screenForgotPassword
import com.shapeup.ui.navigation.routes.auth.routes.forgotPassword.composables.screenSignInVerificationCode
import com.shapeup.ui.utils.constants.Route
import com.shapeup.ui.utils.constants.Screen

fun NavGraphBuilder.routeForgotPassword(navController: NavHostController) {
    navigation(
        route = Route.ForgotPassword.value,
        startDestination = Screen.ForgotPassword.value
    ) {
        screenForgotPassword(navController)
        screenSignInVerificationCode(navController)
        screenChangePassword(navController)
    }
}
