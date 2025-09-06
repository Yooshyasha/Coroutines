package com.yooshyasha

import kotlinx.coroutines.*

suspend fun main() {
    val scope = CoroutineScope(Job() + Dispatchers.Default)

    val job1 = scope.launch {
        delay(3)
        println("Текст номер 1, напечатанный с задержкой в 3 миллисекунды")
    }

    scope.launch {
        println("Текст номер 2, напечатанный без задержки")
    }

    job1.join()
}