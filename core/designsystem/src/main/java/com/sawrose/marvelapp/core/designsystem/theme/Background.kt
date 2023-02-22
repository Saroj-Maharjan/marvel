package com.sawrose.marvelapp.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
* A class to model background colors.
* */
@Immutable
data class Background(
    val color: Color = Color.Unspecified,
    val tonalElevation: Dp = Dp.Unspecified
)

//val localBackgroundTheme = staticCompositionLocalOf {
//    BackgroundTheme()
//}