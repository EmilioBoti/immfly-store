package com.embot.immfly_store.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Spacing(
    val none: Dp = 0.dp,
    val small: Dp = 8.dp,
    val extralNormal: Dp = 12.dp,
    val normal: Dp = 14.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val cardProductRadius: Dp = 30.dp,
    val cardInnerRadius: Dp = 22.dp,
    val cartButtonRadius: Dp = 12.dp,
    val imageHeight: Dp = 230.dp,
    val gapExtralSmall: Dp = 6.dp,
    val gapSmall: Dp = 8.dp,
    val gapMedium: Dp = 16.dp,

    // Text size
    val textSmall: TextUnit = 12.sp,
    val textNormal: TextUnit = 14.sp,
    val textMedium: TextUnit = 16.sp,
    val textMediumExtra: TextUnit = 18.sp,
    val textLarge: TextUnit = 24.sp
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable get() = LocalSpacing.current
