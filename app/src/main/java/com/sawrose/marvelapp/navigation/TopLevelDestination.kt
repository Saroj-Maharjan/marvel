package com.sawrose.marvelapp.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.sawrose.marvelapp.R
import com.sawrose.marvelapp.core.designsystem.icon.MarvelIcons

/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 * */
enum class TopLevelDestination (
    val selectionIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
){
    CHARACTERS(
        selectionIcon = MarvelIcons.homeSelected,
        unselectedIcon = MarvelIcons.homeUnSelected,
        iconTextId = R.string.characters,
        titleTextId = R.string.characters
    ),
    FAVOURITE(
        selectionIcon = MarvelIcons.favouriteSelected,
        unselectedIcon = MarvelIcons.favouriteUnselected,
        iconTextId = R.string.favourite,
        titleTextId = R.string.favourite
    ),
}