package com.sawrose.marvelapp.feature.favourite

import androidx.lifecycle.ViewModel
import com.sawrose.marvelapp.core.domain.usecase.GetCharacterUseCase
import javax.inject.Inject

class FavouriteViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
): ViewModel() {
}