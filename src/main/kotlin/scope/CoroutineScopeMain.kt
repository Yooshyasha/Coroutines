package com.yooshyasha.scope

import kotlinx.coroutines.*

fun main() = runBlocking {
    try {
        coroutineScope {
            val job1 = launch {
                delay(10)
                println("Текст #1, напечатанный с задержкой в 10 мс.")
            }

            val job2 = launch {
                delay(1)
                throw Exception("Ошибка #1, выброшенная через 1 мс.")
            }
        }
    } catch (e: Exception) {
        println("Ошибка: ${e.message}")
    }

    val coroutineScope = CoroutineScope(
        Job() +
                Dispatchers.Default +
                CoroutineExceptionHandler { _, e ->
                    println("Ошибка: ${e.message}")
                }
    )

    val job1 = coroutineScope.launch {
        delay(10)
        println("Текст #2, напечатанный с задержкой в 10 мс.")
    }

    val job2 = coroutineScope.launch {
        delay(1)
        throw Exception("Ошибка #2, выброшенная через 1 мс.")
    }

    job1.join()
}