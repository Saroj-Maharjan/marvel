package com.sawrose.marvelapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.metrics.performance.JankStats
import com.example.compose.MarvelTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sawrose.marvelapp.composable.MainScreen
import com.sawrose.marvelapp.core.data.utils.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Lazily inject [JankStats], which is used to track jank throughout the app.
     */
    @Inject
    lateinit var lazyStats: dagger.Lazy<JankStats>

    private val mainViewmodel by viewModels<MainViewmodel>()

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        var state: MainContract.State by mutableStateOf(
            MainContract.State(
                isLoading = true,
                isSuccess = false
            )
        )
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewmodel.viewState.collect{
                        state = it
                    }
            }
        }

        setContent {
            val systemUiController = rememberSystemUiController()

            MarvelTheme {
                MainScreen(
                    networkMonitor = networkMonitor,
                    windowSizeClass = calculateWindowSizeClass(this),
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lazyStats.get().isTrackingEnabled = true
    }

    override fun onPause() {
        super.onPause()
        lazyStats.get().isTrackingEnabled = false
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MarvelTheme {
        Greeting("Android")
    }
}

