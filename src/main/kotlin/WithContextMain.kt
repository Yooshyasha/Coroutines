package com.yooshyasha

import kotlinx.coroutines.*

fun main() = runBlocking(CoroutineName("runBlocking")) {
    println("Программа запущена в ${coroutineContext[CoroutineName]}")

    withContext(Dispatchers.IO) {
        launch(CoroutineName("withContext")) {
            println("Coroutine выполнена в ${coroutineContext[CoroutineName]}")
        }
    }

    println("Программа завершена в ${coroutineContext[CoroutineName]}")
}