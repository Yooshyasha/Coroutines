package com.yooshyasha

suspend fun main() {
    val cred = getDatabaseCred()
    println(cred)

    val connect = connectDatabase(cred)
    println(connect)
}