package com.learning.routes

import com.learning.plugins.SecurityHandler
import com.learning.ui.Constants
import com.learning.ui.Endpoints
import com.learning.ui.home.HomeTemplate
import com.learning.ui.login.LoginSuccessfulTemplate
import com.learning.ui.login.LoginTemplate
import com.learning.ui.login.LogoutTemplate
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.slf4j.LoggerFactory

data class Session(val username: String)

fun Route.loginRouting() {
    get(Endpoints.LOGIN.url) {
        call.respondHtmlTemplate(LoginTemplate()) {}
    }
    get(Endpoints.HOME.url) {
        call.respondHtmlTemplate(HomeTemplate()) {}
    }
    get(Endpoints.LOGOUT.url) {
        call.sessions.clear(Constants.COOKIE_NAME.value)
        call.respondHtmlTemplate(LogoutTemplate()) {}
    }
    post(Endpoints.DOLOGIN.url) {
        val log = LoggerFactory.getLogger("LoginView")
        val multipart = call.receiveMultipart()

        call.request.headers.forEach { s, list ->
            log.info("key $s values $list")
        }
        var username: String? = null
        var password: String? = null
        while (true) {
            val part = multipart.readPart() ?: break
            when (part) {
                is PartData.FormItem -> {
                    log.info("FormItem: ${part.name} = ${part.value}")
                    when (part.name) {
                        "username" -> username = part.value
                        "password" -> password = part.value
                    }
                }

                is PartData.FileItem -> {
                    log.info("FileItem: ${part.name} -> ${part.originalFileName} of ${part.contentType}")
                }

                else -> {
                    //ignore
                }
            }
            part.dispose()
        }
        if (SecurityHandler().isValid(username, password)) {
            call.sessions.set(Constants.COOKIE_NAME.value, Session(username!!))
            call.respondHtmlTemplate(LoginSuccessfulTemplate()) {
                greeting {
                    +"You are logged in as $username and a cookie has been created"
                }
            }
        } else {
            call.respondHtmlTemplate(LoginTemplate()) {
                greeting {
                    +"Username or password was invalid... Try again."
                }
            }
        }
    }

}