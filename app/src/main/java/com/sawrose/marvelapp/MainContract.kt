package com.sawrose.marvelapp

import com.sawrose.marvelapp.core.common.mvi.MVIEvent
import com.sawrose.marvelapp.core.common.mvi.MVISideEffect
import com.sawrose.marvelapp.core.common.mvi.MVIState

class MainContract {
    sealed class Event: MVIEvent {
        object LoadScreen: Event()
    }

    data class State(
        val isLoading: Boolean,
        val isSuccess: Boolean
    ): MVIState

    sealed class Effect: MVISideEffect {

        sealed class Navigation: Effect() {
            object ToHome: Navigation()
        }
    }
}