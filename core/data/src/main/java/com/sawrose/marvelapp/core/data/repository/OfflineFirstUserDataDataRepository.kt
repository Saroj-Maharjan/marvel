package com.sawrose.marvelapp.core.data.repository

import com.sawrose.marvelapp.core.model.DarkThemeConfig
import com.sawrose.marvelapp.core.datastore.MarvelPreferencesDataSource
import com.sawrose.marvelapp.core.model.ThemeBrand
import com.sawrose.marvelapp.core.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFirstUserDataDataRepository @Inject constructor(
    private val marvelPreferencesDataStore: MarvelPreferencesDataSource
) : UserDataRepository {
    override val userData: Flow<UserData>
        get() = marvelPreferencesDataStore.userData


    override suspend fun updateCharacterFavourite(characterId: String, favourite: Boolean) {
        marvelPreferencesDataStore.toggleCharacterFavourite(characterId, favourite)
    }

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        marvelPreferencesDataStore.setThemeBrand(themeBrand)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        marvelPreferencesDataStore.setDarkThemeConfig(darkThemeConfig)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        marvelPreferencesDataStore.setDynamicColorPreference(useDynamicColor)
    }
}