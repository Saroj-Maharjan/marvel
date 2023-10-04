package com.sawrose.marvelapp.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.sawrose.marvelapp.core.common.network.Dispatcher
import com.sawrose.marvelapp.core.common.network.MarvelDispatchers
import com.sawrose.marvelapp.core.datastore.UserPreferences
import com.sawrose.marvelapp.core.datastore.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(MarvelDispatchers.IO) dispatcher: CoroutineDispatcher,
        userPreferencesSerializer: UserPreferencesSerializer
        ): DataStore<UserPreferences> =
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(dispatcher + SupervisorJob()),
        ){
            context.dataStoreFile("user_preferences.pb")
        }
}