package com.sawrose.marvelapp.composable

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import com.sawrose.marvelapp.core.designsystem.component.MarvelNavigationBar
import com.sawrose.marvelapp.core.designsystem.component.MarvelNavigationBarItem
import com.sawrose.marvelapp.navigation.TopLevelDestination

@Composable
fun MarvelBottomBar(
    destination: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    MarvelNavigationBar(
        modifier = modifier,
    ) {
        destination.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            MarvelNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectionIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(id = destination.iconTextId)) }
            )
        }
    }
}