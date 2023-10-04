package com.sawrose.marvelapp.core.network.api

import com.sawrose.marvelapp.core.common.extensions.toMD5
import com.sawrose.marvelapp.core.network.BuildConfig
import com.sawrose.marvelapp.core.network.MarvelNetworkDataSource
import com.sawrose.marvelapp.core.network.model.BaseResponse
import com.sawrose.marvelapp.core.network.model.CharacterDto
import com.sawrose.marvelapp.core.network.model.NetworkChangeList
import com.sawrose.marvelapp.core.network.retrofit.RetrofitMarvelNetwork
import javax.inject.Inject

class MarvelNetworkDataSourceImpl @Inject constructor(
    val marvelNetwork: RetrofitMarvelNetwork,
): MarvelNetworkDataSource {
    override suspend fun getCharacters(ids: List<String>?): BaseResponse<CharacterDto> {
        val timestamp = System.currentTimeMillis().toString()
        return marvelNetwork.getCharacters(
                apiKey = API_PUBLIC_KEY,
                hash = generateApiHash(timestamp),
                timestamp = timestamp,
                offset = 0,
                limit = 100
            )
    }

    override suspend fun getCharacter(id: String): CharacterDto {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacterChangeList(after: Int?): List<NetworkChangeList> {
        return getCharacters().data.results.mapToChangeList { CharacterDto::id.toString() }
    }
}

/**
 * Converts a list of [T] to change list of all the items in it where [idGetter] defines the
 * [NetworkChangeList.id]
 */
private fun <T> List<T>.mapToChangeList(
    idGetter: (T) -> String,
) = mapIndexed { index, item ->
    NetworkChangeList(
        id = idGetter(item),
        changeListVersion = index,
        isDelete = false,
    )
}


private const val API_PUBLIC_KEY = BuildConfig.MARVEL_API_KEY_PUBLIC
private const val API_PRIVATE_KEY = BuildConfig.MARVEL_API_KEY_PRIVATE
private const val HASH_FORMAT = "%s%s%s"

/**
 * Generate a md5 digest of the timestamp parameter, private API key and public.
 *
 * @param timestamp A digital current record of the time.
 * @return The MD5 Hash
 */
private fun generateApiHash(timestamp: String) =
    HASH_FORMAT.format(timestamp, API_PRIVATE_KEY, API_PUBLIC_KEY).toMD5()