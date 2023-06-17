package com.sawrose.marvelapp.core.common

import app.cash.turbine.test
import com.sawrose.marvelapp.core.common.utils.Result
import com.sawrose.marvelapp.core.common.utils.asResult
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class ResultKtTest {

    @Test
    fun result_catches_result() = runTest {
        flow {
            emit(1)
            throw Exception("Test Done")
        }
            .asResult()
            .test {
                assertEquals(Result.Loading, awaitItem())
                assertEquals(Result.Success(1), awaitItem())

                when(val errorResult = awaitItem()){
                    is Result.Error -> assertEquals(
                        "Test Done",
                        errorResult.exception?.message
                    )
                    Result.Loading,
                    is Result.Success,
                    -> throw IllegalStateException("The Flow should be completed with an error",)
                }
                awaitComplete()
            }
    }
}