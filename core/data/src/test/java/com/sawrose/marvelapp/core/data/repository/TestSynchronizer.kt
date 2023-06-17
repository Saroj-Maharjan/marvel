package com.sawrose.marvelapp.core.data.repository

import com.sawrose.marvelapp.core.data.Synchronizer
import com.sawrose.marvelapp.core.datastore.ChangeListVersion
import com.sawrose.marvelapp.core.datastore.MarvelPreferencesDataSource
import io.mockk.mockk

class TestSynchronizer: Synchronizer {
    val mockDataStore: MarvelPreferencesDataSource = mockk()
    override suspend fun getChangeListVersion(): ChangeListVersion {
        return mockDataStore.getChangeListVersions()
    }

    override suspend fun updateChangeListVersion(version: ChangeListVersion.() -> ChangeListVersion) {
        return mockDataStore.updateChangeListVersion(version)
    }
}