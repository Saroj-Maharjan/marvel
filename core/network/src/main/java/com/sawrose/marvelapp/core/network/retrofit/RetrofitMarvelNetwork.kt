package com.sawrose.marvelapp.core.network.retrofit

import com.sawrose.marvelapp.core.network.model.BaseResponse
import com.sawrose.marvelapp.core.network.model.CharacterDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retorfit Marvel Network for api request
 * */
interface RetrofitMarvelNetwork {
    /**
     * Fetch character List from Marvel API
     *
     * @param apiKey A unique Identifier used to authenticate all requests of API.
     * @param hash A md5 digest of the ts parameter, your private key and your public key (e.g. md5(ts+privateKey+publicKey)
     * @param timestamp A digital timestamp used to authenticate all requests of API.
     * @param offset Skip the specified number of resources in the result set
     * @param limit Limit the number of resources returned in the result set
     * @return Response for marvel characters resource.
     * */
    @GET("v1/public/characters")
    suspend fun getCharacters(
        @Query("apikey")
        apiKey: String,
        @Query("hash")
        hash: String,
        @Query("ts")
        timestamp: String,
        @Query("offset")
        offset: Int,
        @Query("limit")
        limit: Int,
    ): BaseResponse<CharacterDto>

}