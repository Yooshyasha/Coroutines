package com.yooshyasha

import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume


suspend fun getDatabaseCred(): String {
    delay(1)

    return "username:password"
}

suspend fun connectDatabase(cred: String): Boolean {
    delay(1)

    return true
}


fun suspendInKotlin() = runBlocking {
    val cred = getDatabaseCred()

    println(cred)

    val connected = connectDatabase(cred)

    println(connected)
}

fun getDatabaseCredWithContinuation(continuation: Continuation<String>) {
    if (!continuation.context.isActive) {
        return
    }

    return delayResume { continuation.resume("username:password") }
}

fun getDatabaseConnectionWithContinuation(cred: String, continuation: Continuation<Boolean>) {
    if (!continuation.context.isActive) {
        return
    }

    return delayResume { continuation.resume(true) }
}

fun suspendInKotlinWithContinuation() = runBlocking {
    val continuation1 = Continuation(this.coroutineContext) { cred ->
        println(cred)

        val continuation2 = Continuation<Boolean>(this.coroutineContext) { connected ->
            println(connected)
        }

        getDatabaseConnectionWithContinuation(cred.getOrThrow(), continuation2)
    }
    getDatabaseCredWithContinuation(continuation1)
}

fun delayResume(callback: () -> Unit) {
    Thread.sleep(1)
    callback()
}