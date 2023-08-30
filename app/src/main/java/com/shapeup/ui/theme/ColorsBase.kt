package com.shapeup.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val GreenLight = Color(0xFF23C7A8)
val GreenLight50 = Color(0x5023C7A8)
val BackgroundWhite = Color(0xFFFAFAFA)
val BackgroundSecondaryWhite = Color(0xFFF1F1F1)

val GreenDark = Color(0xFF23C7A8)
val GreenDark50 = Color(0xFF10463C)
val BackgroundDark = Color(0xFF2B2B2B)
val BackgroundSecondaryDark = Color(0xFF181818)
val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)

val Pink = Color(0xFFE96BE4)
val BlackText = Color(0xFF2B2B2B)
val WhiteText = Color(0xFFFFFFFF)

val GradientLight = Brush.horizontalGradient(
    listOf(
        BackgroundWhite,
        GreenLight50
    )
)

val GradientDark = Brush.horizontalGradient(
    listOf(
        BackgroundDark,
        GreenDark50
    )
)
