package com.sawrose.marvelapp.core.domain.usecase

import com.sawrose.marvelapp.core.data.repository.MarvelRepository
import com.sawrose.marvelapp.core.model.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: MarvelRepository
){
    operator fun invoke(): Flow<List<Character>> = repository.getCharacters()
}