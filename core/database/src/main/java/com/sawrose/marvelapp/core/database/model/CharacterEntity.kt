package com.sawrose.marvelapp.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sawrose.marvelapp.core.model.Character

/**
 * Defining the Character Entity
 * */
@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val thumbnail: ThumbnailEntity,
)

private const val IMAGE_URL_FORMAT = "%s.%s"

fun CharacterEntity.asExternalModel() = Character(
    id = id,
    name = name,
    description = description,
    imageUrl = IMAGE_URL_FORMAT.format(
        thumbnail.path.replace("http", "https"),
        thumbnail.extension
    )
)

