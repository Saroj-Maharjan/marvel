package com.sawrose.marvelapp

import androidx.lifecycle.viewModelScope
import com.sawrose.marvelapp.MainContract.*
import com.sawrose.marvelapp.core.common.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor():
    BaseViewModel<Event, State, Effect>(){

    init {
        loadScreen()
    }

    override fun setInitialState(): State {
        return State(
            isLoading = true,
            isSuccess = false
        )
    }

    override fun handleEvents(event: Event) {
        when(event){
            is Event.LoadScreen -> setEffect { Effect.Navigation.ToHome }
        }
    }

    private fun loadScreen(){
        viewModelScope.launch {
            setState { copy(isLoading = false, isSuccess = true) }
            delay(2000)
        }
    }
}