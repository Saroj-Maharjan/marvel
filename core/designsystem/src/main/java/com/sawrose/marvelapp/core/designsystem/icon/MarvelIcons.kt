package com.sawrose.marvelapp.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.HomeMax
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Icons for marvel Applications.
 * */
object MarvelIcons {
    val homeSelected = Icons.Default.Home
    val homeUnSelected = Icons.Rounded.HomeMax
    val favouriteSelected = Icons.Rounded.Favorite
    val favouriteUnselected = Icons.Rounded.FavoriteBorder
    val moreVert = Icons.Default.MoreVert
    val settings = Icons.Rounded.Settings
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}