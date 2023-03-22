package com.sawrose.marvelapp.core.model

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
    val isFavourite: Boolean = false,
)
