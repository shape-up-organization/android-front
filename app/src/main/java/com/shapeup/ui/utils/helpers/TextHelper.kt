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

fun toZipCodeMask(input: String): String {
    val maxDigits = 8
    val digitsOnly = input.replace(Regex("\\D"), "")
    val truncatedDigits = digitsOnly.take(maxDigits)

    return when {
        truncatedDigits.length <= 5 -> truncatedDigits
        else -> "${truncatedDigits.substring(0, 5)}-${truncatedDigits.substring(5)}"
    }
}
