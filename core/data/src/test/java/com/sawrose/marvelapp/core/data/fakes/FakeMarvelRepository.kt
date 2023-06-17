package com.sawrose.marvelapp.core.data.fakes

import com.sawrose.marvelapp.core.data.Synchronizer
import com.sawrose.marvelapp.core.data.repository.MarvelRepository
import com.sawrose.marvelapp.core.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * A concrete implementation of [MarvelRepository] to be used for testing.
 * */
class FakeMarvelRepository: MarvelRepository {
    lateinit var allCharacterResult: List<Character>
    private val characterResult :MutableMap<String, Character> = mutableMapOf()

    override fun getCharacters(): Flow<List<Character>> {
        return flowOf(allCharacterResult)
    }

    override fun getCharacter(id: String): Flow<Character> {
        return flowOf(characterResult[id]!!)
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        return false
    }


}