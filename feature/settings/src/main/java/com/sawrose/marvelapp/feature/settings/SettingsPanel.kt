package com.sawrose.marvelapp.feature.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.sawrose.marvelapp.core.model.DarkThemeConfig
import com.sawrose.marvelapp.core.model.ThemeBrand
import com.sawrose.marvelapp.core.model.ThemeBrand.*

@Composable
fun SettingsPanel(
    settings: UserEditableSettings,
    supportDynamicColor: Boolean,
    onChangeThemeBrand: (ThemeBrand) -> Unit,
    onChangeDynamicColorPreference: (Boolean) -> Unit,
    onChangeDarkThemeConfig: (DarkThemeConfig) -> Unit
) {
    SettingsDialogSectionTitle(text = stringResource(id =R.string.theme_settings_title))
    Column(
        modifier = Modifier.selectableGroup()
    ){
        SettingsDialogThemeChooserRow(
            text = stringResource(id = R.string.brand_default),
            selected = settings.themeBrand == DEFAULT,
            onClick = { onChangeThemeBrand(DEFAULT) }
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(id = R.string.brand_android),
            selected = settings.themeBrand == ANDROID,
            onClick = { onChangeThemeBrand(ANDROID) }
        )
    }

    if(settings.themeBrand == DEFAULT && supportDynamicColor) {
        SettingsDialogSectionTitle(text = stringResource(id = R.string.dynamic_color_preference))
        Column(
            modifier = Modifier.selectableGroup()
        ) {
            SettingsDialogThemeChooserRow(
                text = stringResource(id = R.string.dynamic_color_on),
                selected = settings.useDynamicColor,
                onClick = { onChangeDynamicColorPreference(true) }
            )
            SettingsDialogThemeChooserRow(
                text = stringResource(id = R.string.dynamic_color_off),
                selected = !settings.useDynamicColor,
                onClick = { onChangeDynamicColorPreference(false) }
            )
        }
    }

    SettingsDialogSectionTitle(text = stringResource(id = R.string.dark_mode_preference))
    Column(
        modifier = Modifier.selectableGroup()
    ) {
        SettingsDialogThemeChooserRow(
            text = stringResource(id = R.string.dark_mode_config_system_default),
            selected = settings.darkThemeConfig == DarkThemeConfig.FOLLOW_SYSTEM,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.FOLLOW_SYSTEM) }
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(id = R.string.dark_mode_config_system_light),
            selected = settings.darkThemeConfig == DarkThemeConfig.LIGHT,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.LIGHT) }
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(id = R.string.dark_mode_config_system_dark),
            selected = settings.darkThemeConfig == DarkThemeConfig.DARK,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.DARK) }
        )
    }

}

@Composable
fun SettingsDialogSectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
    )
}

@Composable
fun SettingsDialogThemeChooserRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    )  {
        RadioButton(selected = selected, onClick = null)
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}