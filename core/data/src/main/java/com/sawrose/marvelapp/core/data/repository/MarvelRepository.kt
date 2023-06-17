package com.sawrose.marvelapp.core.data.repository

import com.sawrose.marvelapp.core.data.Syncable
import com.sawrose.marvelapp.core.model.Character
import kotlinx.coroutines.flow.Flow

interface MarvelRepository: Syncable {
    /**
     * Get all the Characters from the api
     * */
    fun getCharacters(): Flow<List<Character>>

    fun getCharacter(id: String): Flow<Character>
}