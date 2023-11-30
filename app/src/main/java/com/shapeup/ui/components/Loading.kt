package com.shapeup.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun Loading(compact: Boolean = false) {
    val circleSize = when (compact) {
        true -> 4
        false -> 2
    }
    val strokeWidth = 4.dp

    val foregroundColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background

    CircularProgressIndicator(
        color = backgroundColor,
        modifier = Modifier.drawBehind {
            drawCircle(
                color = foregroundColor,
                radius = size.width / circleSize - strokeWidth.toPx() / circleSize,
                style = Stroke(strokeWidth.toPx())
            )
        }.padding(vertical = 3.dp),
        strokeWidth = strokeWidth
    )
}