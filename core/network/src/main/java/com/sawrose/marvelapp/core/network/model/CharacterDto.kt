package com.sawrose.marvelapp.core.network.model

import com.sawrose.marvelapp.core.model.Character

data class CharacterDto(
    val id: String,
    val name: String,
    val description: String,
    val thumbnail: ThumbnailDto
)


private const val IMAGE_URL_FORMAT = "%s.%s"

fun CharacterDto.asExternalModel() = Character(
    id = id,
    name = name,
    description = description,
    imageUrl = IMAGE_URL_FORMAT.format(
        thumbnail.path.replace("http", "https"),
        thumbnail.extension
    )
)
