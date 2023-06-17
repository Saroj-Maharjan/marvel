package com.sawrose.marvelapp.core.testing.repository

import com.sawrose.marvelapp.core.data.Synchronizer
import com.sawrose.marvelapp.core.data.repository.MarvelRepository
import com.sawrose.marvelapp.core.model.Character
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class TestMarvelRepository: MarvelRepository {
    private val characters: MutableSharedFlow<List<Character>> =
        MutableSharedFlow(replay=1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override fun getCharacters(): Flow<List<Character>> {
        return characters
    }

    override fun getCharacter(id: String): Flow<Character> {
        return characters.map { character ->
            character.find { it.id == id }!!
        }
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        return true
    }
}