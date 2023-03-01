package com.sawrose.marvelapp.core.network.model

data class BaseResponse<T>(
    val code: Int,
    val status: String,
    val message: String,
    val data: DataResponse<T>
)
