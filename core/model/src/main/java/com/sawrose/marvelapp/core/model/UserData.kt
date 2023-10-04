package com.sawrose.marvelapp.core.model

/**
 * Class summerries user data.
 * */
data class UserData(
    val favouriteCharacters: Set<String>,
    val viewedCharacterResource: Set<String>,
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
)