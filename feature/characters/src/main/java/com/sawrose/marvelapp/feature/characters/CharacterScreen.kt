package com.sawrose.marvelapp.feature.characters

import android.os.Build
import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.sawrose.marvelapp.core.designsystem.component.MarvelLoadingWheel
import com.sawrose.marvelapp.core.designsystem.theme.MarvelTheme
import com.sawrose.marvelapp.core.model.Character
import com.sawrose.marvelapp.core.ui.CharacterResourcePreviewParameterProvider
import com.sawrose.marvelapp.core.ui.CharacterUiState
import com.sawrose.marvelapp.core.ui.DevicePreviews
import com.sawrose.marvelapp.core.ui.TrackScrollJank
import com.sawrose.marvelapp.core.ui.characterList

@Composable
internal fun CharacterRoute(
    onCharacterClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharacterViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isSyncing by viewModel.isSyncing.collectAsStateWithLifecycle()

    CharacterScreen(
        isSyncing = isSyncing,
        uiState = uiState,
        onCharacterClick = onCharacterClick,
        modifier = modifier
    )
}

@Composable
internal fun CharacterScreen(
    isSyncing: Boolean,
    uiState: CharacterUiState,
    onCharacterClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isCharacterLoading = uiState is CharacterUiState.Loading

    // This code should be called when the UI is ready for use and relates to Time To Full Display.
    ReportDrawnWhen { !isSyncing && !isCharacterLoading }

    val state = rememberLazyGridState()
    TrackScrollJank(scrollableState = state, stateName = "CharacterScreen:Characters")

    LazyVerticalGrid(
        columns = GridCells.Adaptive(300.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
            .fillMaxSize()
            .testTag("CharacterScreen:Characters"),
        state = state
    ) {
        characterList(
            uiState = uiState,
            onCharacterClick = onCharacterClick,
        )

        item(span = { GridItemSpan(maxLineSpan) }) {
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }
        }
    }
    AnimatedVisibility(
        visible = isSyncing || isCharacterLoading,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> -fullHeight },
        ) + fadeOut(),
    ) {
        val loadingDescription = stringResource(R.string.character_loading_description)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            MarvelLoadingWheel(
                modifier = Modifier
                    .align(Alignment.Center),
                contentDesc = loadingDescription,
            )
        }
    }

//    NotificationPermissionEffect()
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun NotificationPermissionEffect() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
    val notificationsPermissionState = rememberPermissionState(
        android.Manifest.permission.POST_NOTIFICATIONS,
    )
    LaunchedEffect(notificationsPermissionState) {
        val status = notificationsPermissionState.status
        if (status is PermissionStatus.Denied && !status.shouldShowRationale) {
            notificationsPermissionState.launchPermissionRequest()
        }
    }
}

@DevicePreviews
@Composable
fun CharacterScreenLoadingPreview() {
    BoxWithConstraints {
        MarvelTheme {
            CharacterScreen(
                isSyncing = false,
                uiState = CharacterUiState.Loading,
                onCharacterClick = {})
        }
    }
}

@DevicePreviews
@Composable
fun CharacterScreenLoadedPreview(
    @PreviewParameter(CharacterResourcePreviewParameterProvider::class)
    characterResource: List<Character>,
) {
    BoxWithConstraints {
        MarvelTheme {
            CharacterScreen(
                isSyncing = true,
                uiState = CharacterUiState.Characters(characterResource),
                onCharacterClick = {},
            )
        }
    }
}