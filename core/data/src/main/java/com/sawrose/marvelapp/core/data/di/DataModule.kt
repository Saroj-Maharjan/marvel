package com.sawrose.marvelapp.core.data.di

import com.sawrose.marvelapp.core.data.utils.ConnectivityManagerNetworkMonitor
import com.sawrose.marvelapp.core.data.utils.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor
}