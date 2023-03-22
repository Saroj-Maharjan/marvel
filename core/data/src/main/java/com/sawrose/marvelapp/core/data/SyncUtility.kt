package com.sawrose.marvelapp.core.data

import com.sawrose.marvelapp.core.datastore.ChangeListVersion

/**
 * Interface to manage synchronization of data between local and remote data sources for [Syncable].
 * */
interface Synchronizer{
    suspend fun getChangeListVersion(): ChangeListVersion
    suspend fun updateChangeListVersion(version: ChangeListVersion.() -> ChangeListVersion)

    suspend fun Syncable.sync()  = this@sync.syncWith(this@Synchronizer)
}

interface Syncable {
    suspend fun syncWith(synchronizer: Synchronizer): Boolean
}