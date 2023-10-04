package com.sawrose.marvelapp.core.data.fakes

import com.sawrose.marvelapp.core.data.repository.OfflineFirstCharactersRepository
import com.sawrose.marvelapp.core.model.Character
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow

class FakeOfflineFirstCharacterReposirtory {
    val mock: OfflineFirstCharactersRepository = mockk()

    fun mockGetCharacters(
        result: Flow<List<Character>>,
    ) {
        coEvery {
            mock.getCharacters()
        } returns result
    }

    fun mockGetCharacter(
        id: String,
        result: Flow<Character>
    ) {
        coEvery {
            mock.getCharacter(id)
        } returns result
    }


}