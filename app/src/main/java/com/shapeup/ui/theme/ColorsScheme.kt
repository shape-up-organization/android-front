package com.shapeup.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

val LightSchema = lightColorScheme(
    primary = GreenLight,
    secondary = Pink,
    background = BackgroundWhite,
    onBackground = BlackText,
    primaryContainer = BackgroundSecondaryWhite,
    onPrimary = WhiteText
)

val DarkSchema = darkColorScheme(
    primary = GreenDark,
    secondary = Pink,
    background = BackgroundDark,
    onBackground = WhiteText,
    primaryContainer = BackgroundSecondaryDark,
    onPrimary = WhiteText
)
