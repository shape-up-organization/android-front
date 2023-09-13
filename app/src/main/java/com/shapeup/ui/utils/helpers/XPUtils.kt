package com.shapeup.ui.utils.helpers

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object XPUtils {
    private val LEVELS = listOf(
        Level(1, 99, 0),
        Level(2, 199, 100),
        Level(3, 299, 200),
        Level(4, 399, 300),
        Level(5, 499, 400),
        Level(6, 599, 500),
        Level(7, 699, 600)
    )

    private val BORDERS = mapOf(
        1 to Brush.verticalGradient(
            listOf(
                Color(0xFF65E9D0),
                Color(0xFF23C7A8)
            )
        ),
        2 to Brush.verticalGradient(
            listOf(
                Color(0xFFFFE768),
                Color(0xFFFFD700)
            )
        ),
        3 to Brush.verticalGradient(
            listOf(
                Color(0xFFD61717),
                Color(0xFFA00707)
            )
        ),
        4 to Brush.verticalGradient(
            listOf(
                Color(0xFF170DBD),
                Color(0xFF0B0577)
            )
        ),
        5 to Brush.verticalGradient(
            listOf(
                Color(0xFF8E2FC8),
                Color(0xFF8E2FC8)
            )
        ),
        6 to Brush.verticalGradient(
            listOf(
                Color(0xFFBF81E6),
                Color(0xFF9700F3)
            )
        ),
        7 to Brush.verticalGradient(
            listOf(
                Color(0xFF23C7A8),
                Color(0xFFE96BE4)
            )
        )
    )

    private val CONTRAST_COLORS = mapOf(
        1 to Color(0xFF000000),
        2 to Color(0xFF000000),
        3 to Color(0xFF000000),
        4 to Color(0xFFFFFFFF),
        5 to Color(0xFFFFFFFF),
        6 to Color(0xFFFFFFFF),
        7 to Color(0xFFFFFFFF)
    )

    private data class Level(val level: Int, val max: Int, val min: Int)

    fun getLevel(xp: Int): Int {
        if (xp < 0) return 1
        return LEVELS.find { xp >= it.min && xp <= it.max }?.level ?: LEVELS.last().level
    }

    fun getBorder(xp: Int): Brush = BORDERS[getLevel(xp)]!!

    fun getContrastColor(xp: Int): Color? = CONTRAST_COLORS[getLevel(xp)]

    fun getProgress(xp: Int): Int {
        val level = getLevel(xp)
        val (min, max) = LEVELS.find { it.level == level } ?: LEVELS.first()
        return ((xp - min).toFloat() / (max - min).toFloat() * 100).toInt()
    }

    fun getNextLevel(level: Int): Int? =
        LEVELS.find { it.level == level + 1 }?.level

    fun getXpToNextLevel(xp: Int): Int {
        val level = getLevel(xp)
        val max = LEVELS.find { it.level == level }?.max ?: 0
        return max - xp
    }
}
