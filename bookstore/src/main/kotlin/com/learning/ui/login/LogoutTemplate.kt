package com.learning.ui.login

import com.learning.routes.Session
import com.learning.ui.GeneralViewTemplate
import io.ktor.server.html.*
import kotlinx.html.HTML
import kotlinx.html.div
import kotlinx.html.h2

class LogoutTemplate(session: Session? = null) : Template<HTML> {
    private val basicTemplate = GeneralViewTemplate(session)
    override fun HTML.apply() {
        insert(basicTemplate) {
            content {
                div(classes = "mt-2") {
                    h2 { +"You have been logged out!" }
                }
            }
        }
    }
}