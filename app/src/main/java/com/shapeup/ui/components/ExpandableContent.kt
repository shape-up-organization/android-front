package com.shapeup.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

@Composable
fun ExpandableContent(
    content: @Composable () -> Unit,
    transitionDuration: Int = 400,
    visible: Boolean = false
) {
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(transitionDuration)
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(transitionDuration)
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(transitionDuration)
        ) + fadeOut(
            animationSpec = tween(transitionDuration)
        )
    }

    AnimatedVisibility(
        visible = visible,
        enter = enterTransition,
        exit = exitTransition
    ) {
        content()
    }
}
