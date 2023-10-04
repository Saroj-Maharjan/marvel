package com.sawrose.marvelapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sawrose.marvelapp.MainActivityUIState.Loading
import com.sawrose.marvelapp.MainActivityUIState.Success
import com.sawrose.marvelapp.core.data.repository.UserDataRepository
import com.sawrose.marvelapp.core.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(
    userDataRepository: UserDataRepository
): ViewModel(){
    val uiState: StateFlow<MainActivityUIState> = userDataRepository.userData.map {
        Success(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Loading
    )
}

sealed interface MainActivityUIState {
    object Loading : MainActivityUIState
    data class Success(val userData: UserData) : MainActivityUIState
}