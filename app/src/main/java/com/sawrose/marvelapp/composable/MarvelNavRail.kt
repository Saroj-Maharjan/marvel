package com.sawrose.marvelapp.composable

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import com.sawrose.marvelapp.core.designsystem.component.MarvelNavigationRail
import com.sawrose.marvelapp.core.designsystem.component.MarvelNavigationRailItem
import com.sawrose.marvelapp.navigation.TopLevelDestination

@Composable
fun MarvelNavRail(
    destination: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    MarvelNavigationRail(modifier = modifier){
        destination.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            MarvelNavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(imageVector = destination.unselectedIcon, contentDescription = null)
                },
                selectedIcon = {
                   Icon(imageVector = destination.selectionIcon, contentDescription = null)
                },
                label = { Text(stringResource(destination.iconTextId)) },
            )
        }
    }
}