package com.learning.plugins

import com.learning.routes.bookRouting
import com.learning.routes.loginRouting
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(Resources)
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError)
            throw cause
        }
    }
    routing {
        bookRouting()
        loginRouting()
        get("/") {
            call.respondText("Hello World!")
        }
        authenticate("bookStoreAuth") {
            get("/api/tryauth") {
                val principal = call.principal<UserIdPrincipal>()!!
                call.respondText("Hello ${principal.name}")
            }
        }
    }
}
