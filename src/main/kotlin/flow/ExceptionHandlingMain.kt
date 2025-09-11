package com.yooshyasha.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import java.io.IOException

suspend fun main(): Unit = coroutineScope {
    launch(CoroutineName("onEachException")) {
        valueGenerator()
            .onEach {
                throw IOException("Ошибка в onEach")
            }.catch {
                println("Ошибка в ${coroutineContext[CoroutineName]}: $it")
            }.onCompletion {
                println("Блок onCompletion")
            }.collect {
                println(it)
                throw IOException("Ошибка в collect")
            }
    }

    CoroutineScope(
        Job() +
                CoroutineExceptionHandler { context, e ->
                    println("Ошибка в ${context[CoroutineName]}: $e")
                }).launch(CoroutineName("collectException")) {
        valueGenerator().collect {
            throw IOException("Ошибка в collect")
        }
    }
}