package com.sawrose.marvelapp.core.domain.usecase

import com.sawrose.marvelapp.core.data.repository.MarvelRepository
import com.sawrose.marvelapp.core.model.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: MarvelRepository,
) {
    operator fun invoke(id: String): Flow<Character> = repository.getCharacter(id)
}