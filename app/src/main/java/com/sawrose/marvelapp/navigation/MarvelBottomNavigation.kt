package com.sawrose.marvelapp.navigation

import com.sawrose.marvelapp.R
import com.sawrose.marvelapp.core.designsystem.icon.Icon
import com.sawrose.marvelapp.core.designsystem.icon.Icon.DrawableResourceIcon
import com.sawrose.marvelapp.core.designsystem.icon.MarvelIcons

/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 * */
enum class MarvelBottomNavigation (
    val selectionIcon: Icon,
    val unselectedIcon: Icon,
    val iconTextId: Int,
    val titleTextId: Int,
){
    CHARACTERS(
        selectionIcon = DrawableResourceIcon(MarvelIcons.homeSelected),
        unselectedIcon = DrawableResourceIcon(MarvelIcons.homeUnSelected),
        iconTextId = R.string.characters,
        titleTextId = R.string.characters
    ),
    FAVOURITE(
        selectionIcon = DrawableResourceIcon(MarvelIcons.favouriteSelected),
        unselectedIcon = DrawableResourceIcon(MarvelIcons.favouriteUnselected),
        iconTextId = R.string.favourite,
        titleTextId = R.string.favourite
    ),
}