package com.sawrose.marvelapp.core.network

import com.sawrose.marvelapp.core.network.model.BaseResponse
import com.sawrose.marvelapp.core.network.model.CharacterDto
import com.sawrose.marvelapp.core.network.model.NetworkChangeList

/**
 * Interface representing network call for marvel backend.
 * */
interface MarvelNetworkDataSource {
    suspend fun getCharacters(ids: List<String>? = null): BaseResponse<CharacterDto>

    suspend fun getCharacter(id: String): CharacterDto

    suspend fun getCharacterChangeList(after: Int? = null): List<NetworkChangeList>
}