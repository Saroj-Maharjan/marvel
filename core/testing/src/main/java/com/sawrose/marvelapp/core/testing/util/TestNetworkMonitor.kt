package com.sawrose.marvelapp.core.testing.util

import com.sawrose.marvelapp.core.data.utils.NetworkMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TestNetworkMonitor: NetworkMonitor {
    private val connectivityFlow = MutableStateFlow(false)

    override val isOnline: Flow<Boolean> = connectivityFlow

    /**
     * A test-only API to set the connectivity state from tests.
     */
    fun setConnected(isConnected: Boolean){
        connectivityFlow.value = isConnected
    }
}