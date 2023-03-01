package com.sawrose.marvelapp.core.designsystem.component

import android.R
import androidx.annotation.StringRes
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sawrose.marvelapp.core.designsystem.icon.MarvelIcons

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MarvelTopAppBar(
    @StringRes title: Int,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String?,
    actionIcon: ImageVector,
    actionIconContentDescription: String?,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationClick: () -> Unit = { },
    onActionClick: () -> Unit = { }
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = title)) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = colors,
        modifier = modifier.testTag("MarvelTopAppBar"),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarvelTopAppBar(
    @StringRes title: Int,
    actionIcon: ImageVector,
    actionIconContentDescription: String?,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onActionClick: () -> Unit = { }
){
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = title)) },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = colors,
        modifier = modifier.testTag("MarvelTopAppBar"),
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(" MarvelTopBar Preview")
@Composable
fun MarvelTopBarPreview() {
    MarvelTopAppBar(
        title = R.string.untitled,
        navigationIcon = MarvelIcons.moreVert,
        navigationIconContentDescription = "Navigation Icon",
        actionIcon = MarvelIcons.search,
        actionIconContentDescription = "Action Icon",
    )
}