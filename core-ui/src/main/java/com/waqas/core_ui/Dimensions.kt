package com.waqas.core_ui

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.*

data class Dimensions(
    val default: Dp = 0.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceMedium: Dp = 16.dp,
    val spaceLarge: Dp = 32.dp,
)

val LocalSpacing = compositionLocalOf { Dimensions() }