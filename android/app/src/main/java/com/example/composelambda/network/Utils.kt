package com.example.composelambda.network

import okhttp3.MediaType

fun String.toMediaType(): MediaType {
    return MediaType.get(this)
}