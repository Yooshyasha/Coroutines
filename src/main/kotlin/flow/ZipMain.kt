@file:OptIn(ExperimentalCoroutinesApi::class)

package com.yooshyasha.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun valueGenerator(): Flow<Int> = flow {
    for (i in 1..10) {
        delay((Math.random() * 200 - 100).toLong())
        emit((Math.random() * 200 - 100).toInt())
    }
}

suspend fun main(): Unit = coroutineScope {
    launch(CoroutineName("zip")) {
        valueGenerator().zip(valueGenerator()) { value1, value2 ->
            delay(100)
            value1 + value2
        }.collect {
            delay(100)
            println("Got value in ${coroutineContext[CoroutineName]}: $it")
        }
    }

    launch(CoroutineName("combine")) {
        valueGenerator().combine(valueGenerator()) { value1, value2 ->
            delay(100)
            value1 + value2
        }.collect {
            delay(100)
            println("Got value in ${coroutineContext[CoroutineName]}: $it")
        }
    }

    launch(CoroutineName("flattenConcat")) {
        flowOf(valueGenerator(), valueGenerator())
            .flattenConcat()
            .collect {
                delay(100)
                println("Got value in ${coroutineContext[CoroutineName]}: $it")
            }
    }

    launch(CoroutineName("flattenMerge")) {
        flowOf(valueGenerator(), valueGenerator())
            .flattenMerge()
            .collect {
                delay(100)
                println("Got value in ${coroutineContext[CoroutineName]}: $it")
            }
    }
}