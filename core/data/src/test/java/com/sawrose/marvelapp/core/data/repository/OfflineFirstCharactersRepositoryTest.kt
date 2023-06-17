package com.sawrose.marvelapp.core.data.repository

import com.sawrose.marvelapp.core.data.Synchronizer
import com.sawrose.marvelapp.core.database.dao.CharactersDao
import com.sawrose.marvelapp.core.datastore.MarvelPreferencesDataSource
import com.sawrose.marvelapp.core.network.MarvelNetworkDataSource
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class OfflineFirstCharactersRepositoryTest{

    private lateinit var subject: OfflineFirstCharactersRepository
    private var mockCharacterDao: CharactersDao = mockk()
    private var mockNetwork: MarvelNetworkDataSource = mockk()
    private var mockDataStore: MarvelPreferencesDataSource = mockk()

    private lateinit var synchronizer: Synchronizer

    @Before
    fun setUp(){

        synchronizer = TestSynchronizer()
        subject = OfflineFirstCharactersRepository(
            mockDataStore,
            mockCharacterDao,
            mockNetwork
        )
    }

    @Test
    fun offlineFirstCharactersRepository_strean_backed_by_Dao()  {

    }

}