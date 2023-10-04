package com.sawrose.marvelapp.core.common.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val marvelDispatchers: MarvelDispatchers)

enum class MarvelDispatchers {
    IO,
    MAIN,
    DEFAULT,
    UNCONFINED
}