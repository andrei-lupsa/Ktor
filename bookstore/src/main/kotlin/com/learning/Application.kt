package com.learning

import com.learning.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureTemplating()
    configureSecurity()
    configureMonitoring()
    configureHTTP()
    configureSerialization()
    configureRouting()
}
