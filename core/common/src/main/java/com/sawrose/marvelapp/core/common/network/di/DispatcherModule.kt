package com.sawrose.marvelapp.core.common.network.di

import com.sawrose.marvelapp.core.common.network.Dispatcher
import com.sawrose.marvelapp.core.common.network.MarvelDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Dispatcher(MarvelDispatchers.IO)
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(MarvelDispatchers.MAIN)
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Dispatcher(MarvelDispatchers.DEFAULT)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Dispatcher(MarvelDispatchers.UNCONFINED)
    fun provideUnConfirmedDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined

}