package com.sawrose.marvelapp.core.data.model

import com.sawrose.marvelapp.core.database.model.CharacterEntity
import com.sawrose.marvelapp.core.network.model.CharacterDto

fun CharacterDto.asEntity() = CharacterEntity(
    id = id,
    name = name,
    description = description,
    thumbnail = thumbnail.asEntity()
)