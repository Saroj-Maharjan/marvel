package com.sawrose.marvelapp.core.ui

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import com.sawrose.marvelapp.core.model.Character

fun LazyListScope.CharacterCardList(
    items: List<Character>,
    onCharacterClick: (String) -> Unit,
    itemModifier: Modifier = Modifier,
) = items(
    items = items,
    key = { it.id },
    itemContent = { items ->
        CharacterCardExpanded(
            character = items,
            onCharacterClick = { onCharacterClick(items.id) },
            modifier = itemModifier
        )
    }
)