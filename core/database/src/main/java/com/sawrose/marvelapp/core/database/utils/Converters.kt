package com.sawrose.marvelapp.core.database.utils

import androidx.room.TypeConverter
import com.sawrose.marvelapp.core.database.model.ThumbnailEntity

class ThumbnailsConverter{
    @TypeConverter
    fun thumbnailToString(thumbnail: ThumbnailEntity): String {
        return "${thumbnail.path}.${thumbnail.extension}"
    }

    @TypeConverter
    fun stringToThumbnail(thumbnail: String): ThumbnailEntity {
        val split = thumbnail.split(".")
        return ThumbnailEntity(split[0], split[1])
    }
}