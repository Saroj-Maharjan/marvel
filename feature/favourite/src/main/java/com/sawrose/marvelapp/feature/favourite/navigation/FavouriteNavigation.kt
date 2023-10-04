package com.sawrose.marvelapp.feature.favourite.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sawrose.marvelapp.feature.favourite.FavouriteRoute

const val favouriteRoute = "Favourite_route"

fun NavController.navigateToFavourite(navOptions: NavOptions? = null) {
    this.navigate(favouriteRoute, navOptions)
}

fun NavGraphBuilder.favouriteScreen(onFavouriteClick: (String)-> Unit){
    composable(route = favouriteRoute) {
        FavouriteRoute(onFavouriteClick)
    }
}