package com.sawrose.marvelapp.core.data.di

import com.sawrose.marvelapp.core.data.repository.OfflineFirstUserDataDataRepository
import com.sawrose.marvelapp.core.data.repository.UserDataRepository
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
    fun bindsUserDataRepository(
        userDataRepository: OfflineFirstUserDataDataRepository
    ): UserDataRepository


    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor
}