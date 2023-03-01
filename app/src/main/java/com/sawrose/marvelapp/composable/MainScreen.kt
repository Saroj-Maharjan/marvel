package com.sawrose.marvelapp.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sawrose.marvelapp.MainViewmodel
import com.sawrose.marvelapp.R
import com.sawrose.marvelapp.core.designsystem.component.MarvelTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewmodel: MainViewmodel,
    onLauncherFinished: () -> Unit
) {

    Scaffold(
        topBar = {
            MarvelTopAppBar(
                title = R.string.home,
                actionIcon = Icons.Rounded.Search,
                actionIconContentDescription = "Search Icon"
            )
        }) { paddingValues ->
        Text(
            text = stringResource(id = R.string.home),
            modifier = Modifier.padding(paddingValues)
        )

    }
}