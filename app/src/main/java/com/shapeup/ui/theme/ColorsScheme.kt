package com.shapeup.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

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
