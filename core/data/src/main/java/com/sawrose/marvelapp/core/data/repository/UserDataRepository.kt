package com.sawrose.marvelapp.core.data.repository

import com.sawrose.marvelapp.core.model.DarkThemeConfig
import com.sawrose.marvelapp.core.model.ThemeBrand
import com.sawrose.marvelapp.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    /**
     * Stream of [UserData]
     */
    val userData: Flow<UserData>


    suspend fun updateCharacterFavourite(characterId: String, favourite: Boolean)

    /**
     * Set Desired Theme Brand
     * */
    suspend fun setThemeBrand(themeBrand: ThemeBrand)

    /**
     * Set  Desired Dark Theme config
     * */
    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    /**
     * Get Desired Dyanamic Color config
     * */
    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)

}