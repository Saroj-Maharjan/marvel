package com.sawrose.marvelapp.core.database.model

import androidx.room.Entity

/**
 * Defining the Thumbnail Entity
 * */
@Entity(tableName = "thumbnails")
data class ThumbnailEntity(
    val path: String,
    val extension: String
)
