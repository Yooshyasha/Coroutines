package com.yooshyasha

import kotlinx.coroutines.*
import java.io.IOException


suspend fun runCatching() = coroutineScope {
    launch(CoroutineName("runCatching")) {
        kotlin.runCatching {
            getDatabaseCred()
            throw IOException("Ошибка в ${coroutineContext[CoroutineName]}")
        }.onFailure {
            println("Ошибка: $it")
        }
    }
}

suspend fun coroutineExceptionHandler() = supervisorScope {
    launch(CoroutineName("CoroutineExceptionHandler") + CoroutineExceptionHandler { _, throwable ->
        println("Ошибка: $throwable")
    }) {
        getDatabaseCred()
        throw IOException("Ошибка в ${coroutineContext[CoroutineName]}")
    }
}

suspend fun supervisorScope() = supervisorScope {
    launch(CoroutineName("supervisorScope")) {
        getDatabaseCred()
        throw IOException("Ошибка в ${coroutineContext[CoroutineName]}")
    }
}

fun main(): Unit = runBlocking {

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    scope.launch {
        runCatching()
        try {
            coroutineExceptionHandler()
        } catch (e: Exception) {
            println("Ошибка $e прошла наружу")
        }
        try {
            supervisorScope()
        } catch (e: Exception) {
            println("Ошибка $e прошла наружу")
        }
    }.join()

    println("Программа завершилась")
}