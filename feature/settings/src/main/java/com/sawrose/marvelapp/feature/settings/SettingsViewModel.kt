package com.sawrose.marvelapp.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sawrose.marvelapp.core.data.repository.UserDataRepository
import com.sawrose.marvelapp.core.model.DarkThemeConfig
import com.sawrose.marvelapp.core.model.ThemeBrand
import com.sawrose.marvelapp.core.model.UserData
import com.sawrose.marvelapp.feature.settings.SettingUIState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserDataRepository
): ViewModel() {
    val settingsUIState: StateFlow<SettingUIState> =
        userRepository.userData
            .map {userData ->
                Success(
                    settings = UserEditableSettings(
                        themeBrand = userData.themeBrand,
                        useDynamicColor = userData.useDynamicColor,
                        darkThemeConfig = userData.darkThemeConfig
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = Loading
            )

    fun updateThemeBrand(themeBrand: ThemeBrand) {
        viewModelScope.launch {
            userRepository.setThemeBrand(themeBrand)
        }
    }

    fun updateDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        viewModelScope.launch {
            userRepository.setDarkThemeConfig(darkThemeConfig)
        }
    }

    fun updateDynamicColorPreference(useDynamicColor: Boolean) {
        viewModelScope.launch {
            userRepository.setDynamicColorPreference(useDynamicColor)
        }
    }
}
/**
 * Represents the settings which the user can edit within the app.
 */
data class UserEditableSettings(
    val themeBrand: ThemeBrand,
    val useDynamicColor:Boolean,
    val darkThemeConfig: DarkThemeConfig,
)

sealed interface SettingUIState{
    object Loading: SettingUIState
    data class Success(val settings: UserEditableSettings): SettingUIState
}
