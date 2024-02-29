package com.learning.plugins

import com.learning.routes.Session
import com.learning.ui.Constants
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.sessions.*

fun Application.configureSecurity() {
    val users = listOf("shopper1", "shopper2", "shopper3")
    val admins = listOf("admin")

    install(Sessions) {
        cookie<Session>(Constants.COOKIE_NAME.value)
    }

    authentication {
        basic(name = "bookStoreAuth") {
            realm = "Book store"
            validate {
                if ((users.contains(it.name) || admins.contains(it.name))
                    && it.password == "password"
                )
                    UserIdPrincipal(it.name)
                else null
            }
        }
    }
}
