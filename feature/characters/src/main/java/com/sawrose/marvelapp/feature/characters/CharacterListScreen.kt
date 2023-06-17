package com.sawrose.marvelapp.feature.characters

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.sawrose.marvelapp.core.model.Character

@Composable
fun CharacterListScreen(
    modifier: Modifier = Modifier,
    characters: List<Character>,
    onCharacterClick: (String) -> Unit,
    ) {
    
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .testTag("CharacterListScreen:LazyColumn"),
        contentPadding = PaddingValues(16.dp)
    )
    {

        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
        }
    }
}