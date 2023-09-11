package com.shapeup.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val GreenLight = Color(0xFF23C7A8)
val GreenLight50 = Color(0x5023C7A8)
val BackgroundWhite = Color(0xFFFAFAFA)
val BackgroundSecondaryWhite = Color(0xFFF1F1F1)
val GreyLight = Color(0xFF939393)
val GreyLight30 = Color(0x30939393)

val GreenDark = Color(0xFF23C7A8)
val GreenDark50 = Color(0xFF10463C)
val BackgroundDark = Color(0xFF2B2B2B)
val BackgroundSecondaryDark = Color(0xFF181818)

val Pink = Color(0xFFE96BE4)
val BlackText = Color(0xFF2B2B2B)
val WhiteText = Color(0xFFFFFFFF)
val RedError = Color(0xFFED145B)

val GradientLight = Brush.verticalGradient(
    listOf(
        GreenLight50,
        BackgroundWhite
    )
)
val GradientBrush = Brush.linearGradient(
    colors = listOf(RedError, GreenLight),
    start = Offset(0f, 0f),
    end = Offset(200f, 0f)
)

val GradientDark = Brush.verticalGradient(
    listOf(
        GreenDark50,
        BackgroundDark
    )
)
