package com.shapeup.ui.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import com.shapeup.App
import com.shapeup.ui.theme.GradientLight
import com.shapeup.ui.theme.ShapeUpTheme
import kotlinx.coroutines.delay

@Preview
@Composable
fun SplashScreenPreview() {
    ShapeUpTheme {
        SplashScreen {}
    }
}

@Composable
fun SplashScreen(navigateToMainScreen: () -> Unit) {
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
        delay(2000L)
        navigateToMainScreen()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(GradientLight),
    ) {
        Text(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.scale(scale.value),
            style = MaterialTheme.typography.displaySmall,
            text = "ShapeUp"
        )
    }
}