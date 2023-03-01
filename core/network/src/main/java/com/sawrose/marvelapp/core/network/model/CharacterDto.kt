package com.sawrose.marvelapp.core.network.model

data class CharacterDto(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: ThumbnailDto
)
