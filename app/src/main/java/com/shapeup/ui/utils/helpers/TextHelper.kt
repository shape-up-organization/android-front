package com.shapeup.ui.utils.helpers

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class TextHelper(
    val text: String,
    val style: TextStyle? = null,
    val color: Color? = null,
    val paddingStart: Int? = null,
    val paddingTop: Int? = null,
    val paddingEnd: Int? = null
)
