package com.shapeup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.shapeup.ui.screens.SplashScreen
import com.shapeup.ui.screens.auth.SignInScreen
import com.shapeup.ui.screens.auth.SignUpScreen
import com.shapeup.ui.screens.auth.WelcomeScreen
import com.shapeup.ui.screens.auth.forgotPassword.ChangePasswordScreen
import com.shapeup.ui.screens.auth.forgotPassword.ForgotPasswordScreen
import com.shapeup.ui.screens.auth.forgotPassword.VerificationCodeScreen
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Route
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.navigator
import com.shapeup.ui.utils.helpers.viewModel
import com.shapeup.ui.viewModels.auth.SignInFormData
import com.shapeup.ui.viewModels.auth.SignInViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShapeUpTheme {
                App()
            }
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    ShapeUpTheme {
        App()
    }
}

@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.value
    ) {
        composable(route = Screen.Splash.value) {
            SplashScreen(navController.navigator)
        }

        navigation(
            route = Route.Auth.value,
            startDestination = Screen.Welcome.value
        ) {
            composable(route = Screen.Welcome.value) {
                WelcomeScreen(navController.navigator)
            }

            composable(route = Screen.SignIn.value) {
                val viewModel = it.viewModel<SignInViewModel>(navController)

                SignInScreen(
                    data = SignInFormData(
                        email = viewModel.email,
                        password = viewModel.password
                    ),
                    handlers = viewModel.handlers,
                    navigator = navController.navigator
                )
            }

            navigation(
                route = Route.ForgotPassword.value,
                startDestination = Screen.ForgotPassword.value
            ) {
                composable(route = Screen.ForgotPassword.value) {
                    ForgotPasswordScreen(navController.navigator)
                }

                composable(route = Screen.VerificationCode.value) {
                    VerificationCodeScreen(navController.navigator)
                }

                composable(route = Screen.ChangePassword.value) {
                    ChangePasswordScreen(navController.navigator)
                }
            }

            composable(route = Screen.SignUp.value) {
                SignUpScreen()
            }
        }
    }
}
