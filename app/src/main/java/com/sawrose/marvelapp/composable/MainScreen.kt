package com.sawrose.marvelapp.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sawrose.marvelapp.R
import com.sawrose.marvelapp.core.data.utils.NetworkMonitor
import com.sawrose.marvelapp.core.designsystem.component.MarvelBackground
import com.sawrose.marvelapp.core.designsystem.component.MarvelGradientBackground
import com.sawrose.marvelapp.core.designsystem.component.MarvelTopAppBar
import com.sawrose.marvelapp.core.designsystem.theme.GradientColors
import com.sawrose.marvelapp.core.designsystem.theme.LocalGradientColors
import com.sawrose.marvelapp.navigation.MarvelBottomNavigation
import com.sawrose.marvelapp.navigation.MarvelNavHost

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(
    networkMonitor: NetworkMonitor,
    windowSizeClass: WindowSizeClass,
    appState: MarvelAppState = rememberMarvelAppState(
        windowSizeClass = windowSizeClass,
        networkMonitor = networkMonitor
    ),
) {

    val shouldShowGradientBackground =
        appState.currentTopLevelDestination == MarvelBottomNavigation.CHARACTERS

    MarvelBackground {
        MarvelGradientBackground(
            gradientColors = if (shouldShowGradientBackground) {
                LocalGradientColors.current
            } else {
                GradientColors()
            }
        ) {
            val snackbarHostState = remember { SnackbarHostState() }
            val isOffline by appState.isOffline.collectAsStateWithLifecycle()

            // If user is not connected to the internet show a snack bar to inform them.
            val notConnectedMessage = stringResource(R.string.not_connected)
            LaunchedEffect(isOffline) {
                if (isOffline) {
                    snackbarHostState.showSnackbar(
                        message = notConnectedMessage,
                        duration = SnackbarDuration.Indefinite,
                    )
                }
            }
            Scaffold(
                modifier = Modifier.semantics {
                    testTagsAsResourceId = true
                },
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                snackbarHost = { SnackbarHost(snackbarHostState) },
                bottomBar = {
                    if (appState.shouldShowBottomBar){
                        MarvelBottomBar(
                            destination = appState.marvelBottomBarNavigation,
                            onDestinationChanged = appState::navigateToBottomBarNavigation,
                            currentDestination = appState.currentDestination,
                            modifier = Modifier.testTag("MarvelBottomBar")
                        )
                    }
                }
            ) { padding ->
                MarvelContent(
                    appState,
                    Modifier.padding(padding)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarvelContent(
    appState: MarvelAppState,
    modifier: Modifier
) {
    Column(modifier.fillMaxSize()){
        val destination = appState.currentTopLevelDestination
        if (destination!=null){
            MarvelTopAppBar(
                title = R.string.home,
                actionIcon = Icons.Rounded.Search,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                ),
                actionIconContentDescription = "Search Icon"
            )
        }

        MarvelNavHost(
            navController = appState.navController,
        )
    }
}


