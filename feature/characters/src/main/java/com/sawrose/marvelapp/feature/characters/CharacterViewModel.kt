package com.sawrose.marvelapp.feature.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sawrose.marvelapp.core.data.utils.SyncManager
import com.sawrose.marvelapp.core.domain.usecase.GetCharactersUseCase
import com.sawrose.marvelapp.core.ui.CharacterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    syncManager: SyncManager,
    getCharacter: GetCharactersUseCase
): ViewModel() {
    val isSyncing = syncManager.isSyncing
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    val uiState: StateFlow<CharacterUiState> = getCharacter()
        .map(
            CharacterUiState::Characters,
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CharacterUiState.Loading,
        )
}