package com.sawrose.marvelapp.core.datastore

/**
 * Data class to hold the version of the change list for each data source for sync.
* */
data class ChangeListVersion(
    val characterVersion: Int = -1,
)
