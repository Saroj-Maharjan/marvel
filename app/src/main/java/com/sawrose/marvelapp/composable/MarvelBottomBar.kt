package com.sawrose.marvelapp.composable

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.sawrose.marvelapp.core.designsystem.component.MarvelNavigationBar
import com.sawrose.marvelapp.core.designsystem.component.MarvelNavigationBarItem
import com.sawrose.marvelapp.core.designsystem.icon.Icon.DrawableResourceIcon
import com.sawrose.marvelapp.core.designsystem.icon.Icon.ImageVectorIcon
import com.sawrose.marvelapp.navigation.MarvelBottomNavigation

@Composable
fun MarvelBottomBar(
    destination: List<MarvelBottomNavigation>,
    onDestinationChanged: (MarvelBottomNavigation) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
    ) {
    MarvelNavigationBar(
        modifier = modifier,
    ){
        destination.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            MarvelNavigationBarItem(
                selected = selected,
                onClick = { onDestinationChanged(destination) },
                icon = {
                       val icon = if (selected)
                           destination.selectionIcon
                       else
                           destination.unselectedIcon
                    when(icon){
                        is ImageVectorIcon -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = null,
                        )
                        is DrawableResourceIcon -> Icon(
                            painter = painterResource(id = icon.id),
                            contentDescription = null,
                        )
                    }
                },
                label = { Text(stringResource(id = destination.iconTextId)) }
            )
        }
    }
    
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: MarvelBottomNavigation): Boolean {
    return this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
}