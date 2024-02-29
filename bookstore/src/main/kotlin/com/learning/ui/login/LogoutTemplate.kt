package com.learning.ui.login

import com.learning.ui.Endpoints
import com.learning.ui.GeneralViewTemplate
import io.ktor.server.html.*
import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.h2

class LogoutTemplate(val basicTemplate: GeneralViewTemplate = GeneralViewTemplate()) : Template<HTML> {
    override fun HTML.apply() {
        insert(basicTemplate) {
            menu {
                menuitems {
                    a(classes = "nav-link", href = Endpoints.HOME.url) {
                        +"Home"
                    }
                }
                menuitems {
                    a(classes = "nav-link", href = Endpoints.LOGIN.url) {
                        +"Login"
                    }
                }
            }
            content {
                div(classes = "mt-2") {
                    h2 { +"You have been logged out!" }
                }
            }
        }
    }
}