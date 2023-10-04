package com.sawrose.marvelapp.core.testing.util

import com.sawrose.marvelapp.core.data.utils.SyncManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TestSyncManager: SyncManager {

    private val syncStatusFlow = MutableStateFlow(false)

    override val isSyncing: Flow<Boolean> = syncStatusFlow

    override fun requestSync() {
        TODO("Not yet implemented")
    }

    /**
     * A test-only API to set the sync status.
     * */
    fun setSyncStatus(isSyncing: Boolean) {
        syncStatusFlow.value = isSyncing
    }
}