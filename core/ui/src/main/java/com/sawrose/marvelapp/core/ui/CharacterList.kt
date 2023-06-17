package com.sawrose.marvelapp.core.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.sawrose.marvelapp.core.designsystem.theme.MarvelTheme
import com.sawrose.marvelapp.core.model.Character

fun LazyGridScope.characterList(
    uiState: CharacterUiState,
    onCharacterClick: (String) -> Unit,
){
    when(uiState){
        CharacterUiState.Loading -> Unit
        is CharacterUiState.Characters -> {
            items(uiState.characters, key = { it.id }) { character ->
               CharacterCardExpanded(
                        character = character,
                        onCharacterClick = { onCharacterClick(character.id) },
                        modifier = Modifier.padding(8.dp)
                    )
            }
        }
    }
}

@Composable
fun EmptyCharacterList() {
    Text(text = stringResource(R.string.empty_character_screen))
}

sealed interface CharacterUiState{
    data class Characters(
        val characters: List<Character>
    ) : CharacterUiState
    object Loading: CharacterUiState
}

@Preview
@Composable
private fun CharacterListSuccessPreview(
    @PreviewParameter(CharacterResourcePreviewParameterProvider::class)
    characterList: List<Character>
) {
    MarvelTheme() {
        LazyVerticalGrid(columns = GridCells.Adaptive(300.dp)) {
            characterList(
                uiState = CharacterUiState.Characters(
                    characters = characterList
                ),
                onCharacterClick = {},
            )
        }
    }
}