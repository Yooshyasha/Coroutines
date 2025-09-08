package com.yooshyasha

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

suspend fun main() = coroutineScope {
    val deferred = async {
        println("Текст")
        return@async 1
    }

    val result = deferred.await()
    println("Результат: $result")
}