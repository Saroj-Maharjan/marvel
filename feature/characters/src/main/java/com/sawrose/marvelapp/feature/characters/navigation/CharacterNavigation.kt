package com.sawrose.marvelapp.feature.characters.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sawrose.marvelapp.feature.characters.CharacterRoute

const val characterRoute = "Character_route"

fun NavController.navigateToCharacter(navOptions: NavOptions? = null) {
    this.navigate(characterRoute, navOptions)
}

fun NavGraphBuilder.characterScreen(
    onCharacterClick: (String)-> Unit
){
    composable(route = characterRoute) {
        CharacterRoute(onCharacterClick)
    }
}