package com.yooshyasha.flow

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    launch(CoroutineName("buffer")) {
        valueGenerator().zip(valueGenerator()) {value1, value2 ->
            value1 + value2
        }.buffer().collect {
            delay(300)
            println("Got $it in ${coroutineContext[CoroutineName]}")
        }
    }

    launch(CoroutineName("conflate")) {
        valueGenerator().conflate().collect {
            delay(300)
            println("Got $it in ${coroutineContext[CoroutineName]}")
        }
    }

    launch(CoroutineName("collectLatest")) {
        valueGenerator().collectLatest {
            delay(150)
            println("Got $it in ${coroutineContext[CoroutineName]}")
        }
    }
}