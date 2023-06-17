package com.sawrose.marvelapp.feature.characters

import com.sawrose.marvelapp.core.domain.usecase.GetCharactersUseCase
import com.sawrose.marvelapp.core.testing.repository.TestMarvelRepository
import com.sawrose.marvelapp.core.testing.util.MainDispatcherRule
import com.sawrose.marvelapp.core.testing.util.TestSyncManager
import org.junit.Before
import org.junit.Rule


class CharacterViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val syncManager = TestSyncManager()

    private val characterRepository = TestMarvelRepository()

    private val getCharacters = GetCharactersUseCase(
        repository = characterRepository,
    )

//    private val savedStateHandle = SavedStateHandle()
    private lateinit var viewModel: CharacterViewModel

    @Before
    fun setUp() {
      viewModel = CharacterViewModel(
          syncManager = syncManager,
          getCharacter = getCharacters,
      )
    }
}