package com.sawrose.marvelapp.core.network.di

import com.sawrose.marvelapp.core.network.MarvelNetworkDataSource
import com.sawrose.marvelapp.core.network.api.MarvelNetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface FlavoredNetworkModule {

    @Binds
    fun MarvelNetworkDataSourceImpl.binds(): MarvelNetworkDataSource
}