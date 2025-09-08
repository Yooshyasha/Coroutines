package com.yooshyasha.flow

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

fun forValue(): Flow<Int> = flow {
    for (i in 1..10) {
        delay((Math.random() * 200 - 100).toLong())
        emit(i)
    }
}

suspend fun main(): Unit = coroutineScope {
    launch(CoroutineName("first")) {
        forValue().collect {
            println("Got value $it in ${coroutineContext[CoroutineName]}")
        }
    }

    launch(CoroutineName("second")) {
        forValue().collect {
            println("Got value $it in ${coroutineContext[CoroutineName]}")
        }
    }
}