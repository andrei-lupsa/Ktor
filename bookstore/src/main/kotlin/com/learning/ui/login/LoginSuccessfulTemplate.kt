package com.learning.ui.login

import com.learning.ui.Endpoints
import com.learning.ui.GeneralViewTemplate
import io.ktor.server.html.*
import kotlinx.html.*

class LoginSuccessfulTemplate(val basicTemplate: GeneralViewTemplate = GeneralViewTemplate()) : Template<HTML> {
    val greeting = Placeholder<FlowContent>()
    override fun HTML.apply() {
        insert(basicTemplate) {
            menu {
                menuitems {
                    a(classes = "nav-link", href = Endpoints.HOME.url) { +"Home" }
                }
                menuitems {
                    a(classes = "nav-link", href = Endpoints.LOGOUT.url) { +"Logout" }
                }
            }
            content {
                div(classes = "mt-2") {
                    h2 { +"Welcome to the Bookstore" }
                    p {
                        insert(greeting)
                    }
                }
            }
        }
    }
}