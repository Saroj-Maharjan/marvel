package com.sawrose.marvelapp.sync.di

import com.sawrose.marvelapp.core.data.utils.SyncManager
import com.sawrose.marvelapp.sync.status.StubSyncSubscriber
import com.sawrose.marvelapp.sync.status.SyncSubscriber
import com.sawrose.marvelapp.sync.status.WorkManagerSyncManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SyncModule {
    @Binds
    fun bindSyncManager(syncManager: WorkManagerSyncManager): SyncManager

    @Binds
    fun bindsSyncSubscriber(
        syncSubscriber: StubSyncSubscriber,
    ): SyncSubscriber
}