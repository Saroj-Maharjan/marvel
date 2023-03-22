package com.sawrose.marvelapp.composable

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.util.trace
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.sawrose.marvelapp.core.data.utils.NetworkMonitor
import com.sawrose.marvelapp.core.ui.TrackDisposableJank
import com.sawrose.marvelapp.feature.characters.navigation.characterRoute
import com.sawrose.marvelapp.feature.characters.navigation.navigateToCharacter
import com.sawrose.marvelapp.feature.favourite.navigation.favouriteRoute
import com.sawrose.marvelapp.feature.favourite.navigation.navigateToFavourite
import com.sawrose.marvelapp.navigation.MarvelBottomNavigation
import com.sawrose.marvelapp.navigation.MarvelBottomNavigation.CHARACTERS
import com.sawrose.marvelapp.navigation.MarvelBottomNavigation.FAVOURITE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


@Composable
fun rememberMarvelAppState(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): MarvelAppState {
    NavigationTrackingSideEffect(navController)
    return remember(navController, coroutineScope, windowSizeClass, networkMonitor) {
        MarvelAppState(
            navController = navController,
            coroutineScope = coroutineScope,
            windowSizeClass = windowSizeClass,
            networkMonitor = networkMonitor,
        )
    }
}


@Stable
class MarvelAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: MarvelBottomNavigation?
        @Composable get() = when (currentDestination?.route) {
            characterRoute -> CHARACTERS
            favouriteRoute -> FAVOURITE
            else -> null
        }

    var shouldShowSettingsDialog by mutableStateOf(false)
        private set

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    /**
     * Map of Marvel BottomBar destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val marvelBottomBarNavigation: List<MarvelBottomNavigation> =
        MarvelBottomNavigation.values().asList()


    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param MarvelBottomNavigation: The destination the app needs to navigate to.
     */
    fun navigateToBottomBarNavigation(marvelBottomBarNavigation: MarvelBottomNavigation) {
        trace("Navigation: ${marvelBottomBarNavigation.name}") {
            val topLevelNavOption = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }

            when (marvelBottomBarNavigation) {
                CHARACTERS -> navController.navigateToCharacter(topLevelNavOption)
                FAVOURITE -> navController.navigateToFavourite(topLevelNavOption)
            }
        }
    }

    fun setShowSettingsDialog(show: Boolean) {
        shouldShowSettingsDialog = show
    }

}

/**
 * Stores information about navigation events to be used with JankStats
 */
@Composable
private fun NavigationTrackingSideEffect(navController: NavHostController) {
    TrackDisposableJank(navController) { metricsHolder ->
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            metricsHolder.state?.putState("Navigation", destination.route.toString())
        }

        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }
}
