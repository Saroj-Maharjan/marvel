package com.sawrose.marvelapp.core.common.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event: ViewEvent, UIState: ViewState, Effect: ViewSideEffect>: ViewModel() {

    private val initialState: UIState by lazy { setInitialState() }
    abstract fun setInitialState(): UIState

    // Using stateFlow instead of LiveData with initial state
    private val _viewState: MutableStateFlow<UIState> = MutableStateFlow(initialState)
    val viewState: StateFlow<UIState> = _viewState

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    fun sendEvent(event: Event){
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    protected fun setState(reducer: UIState.() -> UIState){
        _viewState.value = _viewState.value.reducer()
    }

    private fun subscribeToEvents(){
        viewModelScope.launch {
            _event.collect { event ->
                handleEvents(event)
            }
        }
    }

    abstract fun handleEvents(event: Event)

    protected fun setEffect(builder: () -> Effect){
        val effectual = builder()
        viewModelScope.launch {
            _effect.send(effectual)
        }
    }
}