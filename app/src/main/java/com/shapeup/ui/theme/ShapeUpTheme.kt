package com.shapeup.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ShapeUpTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkSchema else LightSchema

    MaterialTheme(
        colorScheme = colors,
        content = content,
        typography = Typography
    )
}
