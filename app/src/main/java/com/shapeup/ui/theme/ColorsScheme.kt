package com.shapeup.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val LightSchema = lightColorScheme(
    primary = GreenLight,
    primaryContainer = BackgroundSecondaryWhite,
    onPrimary = WhiteText,
    secondary = Pink,
    tertiary = GreyLight,
    tertiaryContainer = GreyLight30,
    background = BackgroundWhite,
    onBackground = BlackText
)

val DarkSchema = darkColorScheme(
    primary = GreenDark,
    primaryContainer = BackgroundSecondaryDark,
    onPrimary = WhiteText,
    secondary = Pink,
    tertiary = GreyLight,
    tertiaryContainer = GreyLight30,
    background = BackgroundDark,
    onBackground = WhiteText
)

enum class LevelGradient(val value: Brush) {
    LEVEL_1(
        value = Brush.verticalGradient(
            listOf(
                Color(0xFF23C7A8),
                Color(0xFFE96BE4)
            )
        )
    )
}
