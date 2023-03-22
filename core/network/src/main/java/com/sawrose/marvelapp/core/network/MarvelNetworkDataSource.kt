package com.sawrose.marvelapp.core.network

import com.sawrose.marvelapp.core.network.model.BaseResponse
import com.sawrose.marvelapp.core.network.model.CharacterDto

/**
 * Interface representing network call for marvel backend.
 * */
interface MarvelNetworkDataSource {
    suspend fun getCharacters(): BaseResponse<CharacterDto>
}