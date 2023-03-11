package com.sawrose.marvelapp.feature.characters

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun CharacterRoute(
    onCharacterClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    CharacterScreen(
        onCharacterClick = onCharacterClick,
        modifier = modifier
    )

}

@Composable
fun CharacterScreen(
    onCharacterClick: (String) -> Unit,
//    saveCharacters: () -> Unit,
    modifier: Modifier = Modifier,
) {

}