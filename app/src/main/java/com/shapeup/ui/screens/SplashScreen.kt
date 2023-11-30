package com.shapeup.ui.screens

import android.content.Context
import android.view.animation.OvershootInterpolator
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.shapeup.MainActivity
import com.shapeup.R
import com.shapeup.api.utils.helpers.SharedData
import com.shapeup.ui.theme.GradientDark
import com.shapeup.ui.theme.GradientLight
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.auth.AuthData
import com.shapeup.ui.viewModels.auth.AuthHandlers
import com.shapeup.ui.viewModels.auth.authDataMock
import com.shapeup.ui.viewModels.auth.authHandlersMock
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.delay
import kotlin.system.exitProcess

@Preview
@Composable
fun SplashScreenPreview() {
    ShapeUpTheme {
        SplashScreen(
            data = authDataMock,
            handlers = authHandlersMock,
            navigator = Navigator()
        )
    }
}

@Composable
fun SplashScreen(
    data: AuthData,
    handlers: AuthHandlers,
    navigator: Navigator
) {
    val scale = remember {
        Animatable(0.3f)
    }

    val sharedData =
        SharedData(
            LocalContext.current.getSharedPreferences(
                "shapeup",
                Context.MODE_PRIVATE
            )
        )

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(600L)

        val emailShared = sharedData.get("email")
        val passwordShared = sharedData.get("password")

        when {
            !emailShared.isNullOrBlank() && !passwordShared.isNullOrBlank() -> {
                data.email.value = emailShared
                data.password.value = passwordShared

                val response = handlers.signIn()

                when (response.status) {
                    HttpStatusCode.OK -> navigator.navigate(Screen.Feed)

                    else -> navigator.navigate(Screen.Welcome)
                }
            }

            else -> navigator.navigate(Screen.Welcome)
        }
    }

    BackHandler {
        MainActivity().finish()
        exitProcess(0)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(
                when {
                    isSystemInDarkTheme() -> GradientDark
                    else -> GradientLight
                }
            )
    ) {
        Text(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.scale(scale.value),
            style = MaterialTheme.typography.displaySmall,
            text = stringResource(R.string.app_name)
        )
    }
}
