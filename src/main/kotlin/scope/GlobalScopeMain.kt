package com.yooshyasha.scope

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
suspend fun main() {
    val job = GlobalScope.launch {
        delay(10)
        println("Задача, завершённая с задержкой в 10 миллисекунд")
    }

    val job2 = GlobalScope.launch {
        delay(1)
        println("Задача, завершённая с задержкой в миллисекунду")
    }

    val job3 = GlobalScope.launch {
        delay(1)
        println("Задача, завершённая с задержкой в миллисекунду")
    }

    val job4 = GlobalScope.launch {
        delay(1)
        throw Exception("Задача, завершенная с ошибкой")
    }

    job.join()
}