package com.sawrose.marvelapp.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.sawrose.marvelapp.core.designsystem.R

/**
 * Icons for marvel Applications.
 * */
object MarvelIcons {
    val homeSelected = R.drawable.ic_home
    val homeUnSelected = R.drawable.ic_home_outlined
    val favouriteSelected = R.drawable.ic_favorite
    val favouriteUnselected = R.drawable.ic_favorite_boarder
    val moreVert = Icons.Default.MoreVert
    val search = Icons.Rounded.Search
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}