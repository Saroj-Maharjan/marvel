package com.sawrose.marvelapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sawrose.marvelapp.core.database.dao.CharactersDao
import com.sawrose.marvelapp.core.database.model.CharacterEntity
import com.sawrose.marvelapp.core.database.utils.ThumbnailsConverter

@Database(
    entities = [
        CharacterEntity::class
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(ThumbnailsConverter::class)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
}