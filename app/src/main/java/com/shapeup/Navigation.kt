package com.shapeup

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shapeup.ui.screens.SignInScreen
import com.shapeup.ui.screens.SignUpScreen
import com.shapeup.ui.screens.SplashScreen
import com.shapeup.ui.screens.WelcomeScreen
import com.shapeup.ui.utils.constants.Screens
import com.shapeup.ui.viewModels.StartupViewModel

@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    val viewModel = StartupViewModel()

    NavHost(
        navController = navController,
        startDestination = Screens.Splash.name
    ) {
        composable(route = Screens.Splash.name) {
            SplashScreen(navigateToMainScreen = {
                navController.navigate(Screens.Welcome.name)
            })
        }
        composable(route = Screens.SignIn.name) {
            SignInScreen()
        }
        composable(route = Screens.Welcome.name) {
            WelcomeScreen(
                navigateToSignIn = {
                    navController.navigate(Screens.SignIn.name)
                },
                navigateToSignUp = { navController.navigate(Screens.SignUp.name) }
            )
        }
        composable(route = Screens.SignUp.name) {
            SignUpScreen()
        }
    }
}
