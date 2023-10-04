package com.sawrose.marvelapp.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.sawrose.marvelapp.core.designsystem.icon.MarvelIcons
import com.sawrose.marvelapp.core.designsystem.theme.GradientColors
import com.sawrose.marvelapp.core.designsystem.theme.LocalGradientColors
import com.sawrose.marvelapp.feature.settings.SettingsDialog
import com.sawrose.marvelapp.navigation.MarvelNavHost
import com.sawrose.marvelapp.navigation.TopLevelDestination

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class,
    ExperimentalLayoutApi::class
)
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
        appState.currentTopLevelDestination == TopLevelDestination.CHARACTERS

    var showSettingsDialog by rememberSaveable {
        mutableStateOf(false)
    }

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
                        duration = Indefinite,
                    )
                }
            }
            if (showSettingsDialog) {
                SettingsDialog(
                    onDismiss = { showSettingsDialog = false },
                )
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
                    if (appState.shouldShowBottomBar) {
                        MarvelBottomBar(
                            destination = appState.marvelBottomBarNavigation,
                            onNavigateToDestination = appState::navigateToBottomBarNavigation,
                            currentDestination = appState.currentDestination,
                            modifier = Modifier.testTag("MarvelBottomBar")
                        )
                    }
                }
            ) { padding ->
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .consumedWindowInsets(padding)
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal
                            )
                        )
                ) {
                    if (appState.shouldShowNavRail) {
                        MarvelNavRail(
                            destination = appState.marvelBottomBarNavigation,
                            onNavigateToDestination = appState::navigateToBottomBarNavigation,
                            currentDestination = appState.currentDestination,
                            modifier = Modifier
                                .testTag("MarvelNavRail")
                                .safeDrawingPadding(),
                        )
                    }
                }
                Column(
                    Modifier
                        .fillMaxSize()
                ) {
                    val destination = appState.currentTopLevelDestination
                    if (destination != null) {
                        MarvelTopAppBar(
                            title = R.string.home,
                            actionIcon = MarvelIcons.settings,
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color.Transparent,
                            ),
                            actionIconContentDescription = "Search Icon",
                            onActionClick = { showSettingsDialog = true },
                        )
                    }

                    MarvelNavHost(
                        appState = appState,
                        onShowSnackbar = { message, action ->
                            snackbarHostState.showSnackbar(
                                message = message,
                                actionLabel = action,
                                duration = SnackbarDuration.Short,
                            ) == SnackbarResult.ActionPerformed
                        })
                }
            }
        }
    }
}


