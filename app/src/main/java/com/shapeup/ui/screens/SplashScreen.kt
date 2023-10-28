package com.shapeup.ui.screens

import android.view.animation.OvershootInterpolator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.shapeup.R
import com.shapeup.ui.theme.GradientDark
import com.shapeup.ui.theme.GradientLight
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.viewModels.auth.AuthHandlers
import com.shapeup.ui.viewModels.auth.authHandlersMock
import kotlinx.coroutines.delay

@Preview
@Composable
fun SplashScreenPreview() {
    ShapeUpTheme {
        SplashScreen(
            handlers = authHandlersMock
        )
    }
}

@Composable
fun SplashScreen(
    handlers: AuthHandlers
) {
    val scale = remember {
        Animatable(0.3f)
    }

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
        handlers.startupVerification()
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
