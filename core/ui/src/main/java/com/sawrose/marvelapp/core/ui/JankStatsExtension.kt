package com.sawrose.marvelapp.core.ui

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalView
import androidx.metrics.performance.PerformanceMetricsState
import androidx.metrics.performance.PerformanceMetricsState.Holder
import kotlinx.coroutines.CoroutineScope

/**
 * Retrieves [PerformanceMetricsState.Holder] from current [LocalView] and
 * remembers it until the View changes.
 * @see PerformanceMetricsState.getHolderForHierarchy
 * */
@Composable
fun rememberMetricsStateHolder(): Holder {
    val localView = LocalView.current
    return remember(localView) {
        PerformanceMetricsState.getHolderForHierarchy(localView)
    }
}

/**
 * Convenience function to work with [PerformanceMetricsState] state that needs to be cleaned up.
 * The side effect is re-launched if any of the [keys] value is not equal to the previous composition.
 * */
@Composable
fun TrackJank(
    vararg Keys: Any?,
    reportMetrics: suspend CoroutineScope.(state: Holder) -> Unit,
) {
    val metrics = rememberMetricsStateHolder()
    LaunchedEffect(metrics, *Keys) {
        reportMetrics(metrics)
    }
}

/**
 * Convenience function to work with [PerformanceMetricsState] state that needs to be cleaned up.
 * The side effect is re-launched if any of the [keys] value is not equal to the previous composition.
 */
@Composable
fun TrackDisposableJank(
    vararg keys: Any?,
    reportMetric: DisposableEffectScope.(state: Holder) -> DisposableEffectResult,
) {
    val metrics = rememberMetricsStateHolder()
    DisposableEffect(metrics, *keys) {
        reportMetric(this, metrics)
    }
}

/**
 * Track jank while scrolling anything that's scrollable.
 */
@Composable
fun TrackScrollJank(scrollableState: ScrollableState, stateName: String) {
    TrackJank(scrollableState) { metricsHolder ->
        snapshotFlow { scrollableState.isScrollInProgress }.collect { isScrollInProgress ->
            metricsHolder.state?.apply {
                if (isScrollInProgress) {
                    putState(stateName, "Scrolling=true")
                } else {
                    removeState(stateName)
                }
            }
        }
    }
}