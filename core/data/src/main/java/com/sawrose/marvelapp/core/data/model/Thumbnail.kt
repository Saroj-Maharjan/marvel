package com.sawrose.marvelapp.core.data.model

import com.sawrose.marvelapp.core.database.model.ThumbnailEntity
import com.sawrose.marvelapp.core.network.model.ThumbnailDto

fun ThumbnailDto.asEntity() = ThumbnailEntity(
    path = path,
    extension = extension
)