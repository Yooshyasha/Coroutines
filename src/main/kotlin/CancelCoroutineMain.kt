package com.yooshyasha

import kotlinx.coroutines.*

suspend fun main(): Unit = coroutineScope {
    val job = launch {
        println("Запуск 1 coroutine")
        val cred = getDatabaseCred()

        launch {
            println("Запуск 2 coroutine")
            connectDatabase(cred)
            println("Конец 2 coroutine")
        }.join()

        println("Конец 1 coroutine")
    }

    launch {
        println("Запуск 3 coroutine")
        getDatabaseCred()
        println("Конец 3 coroutine")
    }

    delay(5)
    job.cancel()
}