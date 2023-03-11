package com.sawrose.marvelapp.core.data.utils

import kotlinx.coroutines.flow.Flow

/**
 * Utility to report app connectivity status
 * */
interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}