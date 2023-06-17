package com.sawrose.marvelapp.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import com.sawrose.marvelapp.core.model.DarkThemeConfig
import com.sawrose.marvelapp.core.model.ThemeBrand
import com.sawrose.marvelapp.core.model.UserData
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class MarvelPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    val userData = userPreferences.data
        .map {
            UserData(
                favouriteCharacters = it.characterFavouriteIdsMap.keys,
                viewedCharacterResource = it.viewedCharacterIdsMap.keys,
                themeBrand = when(it.themeBrand) {
                    null,
                    ThemeBrandProto.THEME_BRAND_UNSPECIFIED,
                    ThemeBrandProto.UNRECOGNIZED,
                    ThemeBrandProto.THEME_BRAND_DEFAULT,
                    ->  ThemeBrand.DEFAULT
                        ThemeBrandProto.THEME_BRAND_ANDROID
                    -> ThemeBrand.ANDROID
                },
                darkThemeConfig = when(it.darkThemeConfig) {
                        null,
                        DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
                        DarkThemeConfigProto.UNRECOGNIZED,
                        DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM,
                        ->
                        DarkThemeConfig.FOLLOW_SYSTEM
                    DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                        ->
                        DarkThemeConfig.LIGHT
                    DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                        -> DarkThemeConfig.DARK
                                                           },
                useDynamicColor = it.useDynamicColor
            )
        }

    suspend fun toggleCharacterFavourite(characterId: String, followed: Boolean){
        try{
            userPreferences.updateData {
                it.copy {
                    if(followed){
                        characterFavouriteIds.put(characterId, true)
                    }else{
                        characterFavouriteIds.remove(characterId)
                    }
                }
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }


    suspend fun setThemeBrand(themeBrand: ThemeBrand){
        try{
            userPreferences.updateData {
                it.copy {
                    this.themeBrand = when(themeBrand){
                        ThemeBrand.DEFAULT -> ThemeBrandProto.THEME_BRAND_DEFAULT
                        ThemeBrand.ANDROID -> ThemeBrandProto.THEME_BRAND_ANDROID
                    }
                }
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig){
        try{
            userPreferences.updateData {
                it.copy {
                    this.darkThemeConfig = when(darkThemeConfig){
                        DarkThemeConfig.FOLLOW_SYSTEM -> DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM
                        DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                        DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                    }
                }
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean){
        try{
            userPreferences.updateData {
                it.copy {
                    this.useDynamicColor = useDynamicColor
                }
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    suspend fun setCharacterResourceViewed(characterResourceId: String, viewed: Boolean) {
        setCharacterResourcesViewed(listOf(characterResourceId), viewed)
    }

    suspend fun setCharacterResourcesViewed(characterResourceIds: List<String>, viewed: Boolean) {
        userPreferences.updateData { prefs ->
            prefs.copy {
                characterResourceIds.forEach { id ->
                    if (viewed) {
                        viewedCharacterIds.put(id, true)
                    } else {
                        viewedCharacterIds.remove(id)
                    }
                }
            }
        }
    }

    suspend fun getChangeListVersions() = userPreferences.data
        .map{
            ChangeListVersion(
                characterVersion = it.characterChangeListVersion
            )
        }.firstOrNull() ?: ChangeListVersion()

    /**
     * Update the [ChangeListVersions] using [update].
     */
    suspend fun updateChangeListVersion(update: ChangeListVersion.() -> ChangeListVersion){
        try{
            userPreferences.updateData {currentPreference ->
                val updateChangeListVersions = update(
                    ChangeListVersion(
                        characterVersion = currentPreference.characterChangeListVersion
                    ),
                )

                currentPreference.copy {
                    characterChangeListVersion = updateChangeListVersions.characterVersion
                }
            }
        } catch (exception: IOException){
            Log.e("MarvelPreferences", "Failed to update user preferences", exception)
        }
    }
}