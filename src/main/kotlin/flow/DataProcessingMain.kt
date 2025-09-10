package com.yooshyasha.flow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    launch {
        valueGenerator().onEach {
            it * 2
        }.collectLatest { println("Обработка onEach collectLatest $it") }
    }

    launch {
        valueGenerator().onEach {
            val result = it * 2
            println("Обработка onEach $it -> $result")
        }.launchIn(this)  // this - CoroutineScope
    }
}