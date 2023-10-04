package com.sawrose.marvelapp.feature.favourite

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
internal fun FavouriteRoute(
    onFavouriteClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FavouriteScreen(
        onFavouriteClick = onFavouriteClick,
        modifier = modifier
    )

}

@Composable
fun FavouriteScreen(
    onFavouriteClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

}