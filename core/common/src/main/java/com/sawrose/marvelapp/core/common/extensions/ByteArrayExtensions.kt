package com.sawrose.marvelapp.core.common.extensions

/**
 * Convert ByteArray to Hex String
 *
 * @return Hex String
 * */
fun ByteArray.toHex() =
    joinToString("") {
        "%02x".format(it)
    }
