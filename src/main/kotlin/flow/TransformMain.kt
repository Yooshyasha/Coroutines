package com.yooshyasha.flow

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch


@Suppress("DuplicatedCode")
suspend fun main(): Unit = coroutineScope {
    launch(CoroutineName("first")) {
        forValue()
            .filter {
                delay(100)
                true
            }
            .transform<Int, Long> {
                delay(100)
                it.toLong()
            }
            .map {
                delay(100)
            }
            .collect {
                println("Got value $it in ${coroutineContext[CoroutineName]}")
            }
    }

    launch(CoroutineName("second")) {
        forValue()
            .filter {
                delay(100)
                true
            }
            .transform<Int, Long> {
                delay(100)
                it.toLong()
            }
            .map {
                delay(100)
            }
            .collect {
                println("Got value $it in ${coroutineContext[CoroutineName]}")
            }
    }
}