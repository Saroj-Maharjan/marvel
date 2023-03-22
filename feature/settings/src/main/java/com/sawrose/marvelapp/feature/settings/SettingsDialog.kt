package com.sawrose.marvelapp.feature.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.compose.MarvelTheme
import com.example.compose.supportsDynamicTheming
import com.sawrose.marvelapp.core.model.DarkThemeConfig
import com.sawrose.marvelapp.core.model.DarkThemeConfig.*
import com.sawrose.marvelapp.core.model.ThemeBrand
import com.sawrose.marvelapp.core.model.ThemeBrand.DEFAULT
import com.sawrose.marvelapp.feature.settings.SettingUIState.Loading
import com.sawrose.marvelapp.feature.settings.SettingUIState.Success

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settingsUIState by viewModel.settingsUIState.collectAsStateWithLifecycle()
    
    SettingsDialog(
        onDismiss = onDismiss,
        settingUIState = settingsUIState,
        onChangeThemeBrand = viewModel::updateThemeBrand,
        onChangeDynamicColorPreference = viewModel::updateDynamicColorPreference,
        onChangeDarkThemeConfig = viewModel::updateDarkThemeConfig,
    )
    
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsDialog(
    settingUIState: SettingUIState,
    supportDynamicColor: Boolean = supportsDynamicTheming(),
    onDismiss: () -> Unit,
    onChangeThemeBrand: (themeBrand: ThemeBrand) -> Unit,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit
) {
    val configuration = LocalConfiguration.current

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp- 80.dp),
        onDismissRequest = {onDismiss},
        title = {
                Text(
                    text = stringResource(id = R.string.settings_title),
                    style = MaterialTheme.typography.titleLarge,
                )
        },

        text = {
            Divider()
            Column(Modifier.verticalScroll(rememberScrollState())) {
                when(settingUIState){
                    Loading -> {
                        Text(
                            text = stringResource(R.string.loading),
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }

                    is Success -> {
                        SettingsPanel(
                            settings = settingUIState.settings,
                            supportDynamicColor = supportDynamicColor,
                            onChangeThemeBrand = onChangeThemeBrand,
                            onChangeDynamicColorPreference = onChangeDynamicColorPreference,
                            onChangeDarkThemeConfig = onChangeDarkThemeConfig,
                        )
                    }
                }
            }
        },
        confirmButton = {
            Text(
                text = stringResource(id = R.string.dismiss_dialog_button_text),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDismiss() }
            )
        },
    )
}


@Preview
@Composable
private fun PreviewSettingsDialog() {
    MarvelTheme {
        SettingsDialog(
            onDismiss = {},
            settingUIState = Success(
                UserEditableSettings(
                    themeBrand = DEFAULT,
                    darkThemeConfig = FOLLOW_SYSTEM,
                    useDynamicColor = false,
                ),
            ),
            onChangeThemeBrand = {},
            onChangeDynamicColorPreference = {},
            onChangeDarkThemeConfig = {},
        )
    }
}

@Preview
@Composable
private fun PreviewSettingsDialogLoading() {
    MarvelTheme {
        SettingsDialog(
            onDismiss = {},
            settingUIState = Loading,
            onChangeThemeBrand = {},
            onChangeDynamicColorPreference = {},
            onChangeDarkThemeConfig = {},
        )
    }
}