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
import com.shapeup.ui.screens.forgotPassword.ChangePasswordScreen
import com.shapeup.ui.screens.forgotPassword.ForgotPasswordScreen
import com.shapeup.ui.screens.forgotPassword.VerificationCodeScreen
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

        composable(route = Screens.Welcome.name) {
            WelcomeScreen(
                navigateToSignIn = {
                    navController.navigate(Screens.SignIn.name)
                },
                navigateToSignUp = { navController.navigate(Screens.SignUp.name) }
            )
        }

        composable(route = Screens.SignIn.name) {
            SignInScreen(
                navigateToWelcome = {
                    navController.navigate(Screens.Welcome.name)
                },
                navigateToForgotPassword = {
                    navController.navigate(Screens.ForgotPassword.name)
                },
                navigateToSignIn = {
                    navController.navigate(Screens.SignIn.name)
                },
                navigateToSignUp = {
                    navController.navigate(Screens.SignUp.name)
                }
            )
        }

        composable(route = Screens.ForgotPassword.name) {
            ForgotPasswordScreen(
                navigateToSignIn = {
                    navController.navigate(Screens.SignIn.name)
                },
                navigateToVerificationCode = {
                    navController.navigate(Screens.VerificationCode.name)
                }
            )
        }

        composable(route = Screens.VerificationCode.name) {
            VerificationCodeScreen(
                navigateToForgotPassword = {
                    navController.navigate(Screens.ForgotPassword.name)
                },
                navigateToChangePassword = {
                    navController.navigate(Screens.ChangePassword.name)
                }
            )
        }

        composable(route = Screens.ChangePassword.name) {
            ChangePasswordScreen(
                navigateToVerificationCode = {
                    navController.navigate(Screens.VerificationCode.name)
                }
            )
        }

        composable(route = Screens.SignUp.name) {
            SignUpScreen()
        }
    }
}
