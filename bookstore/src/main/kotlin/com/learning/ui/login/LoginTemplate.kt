package com.learning.ui.login

import com.learning.ui.Endpoints
import com.learning.ui.GeneralViewTemplate
import io.ktor.server.html.*
import kotlinx.html.*

class LoginTemplate(val basicTemplate: GeneralViewTemplate = GeneralViewTemplate()) : Template<HTML> {
    val greeting = Placeholder<FlowContent>()
    override fun HTML.apply() {
        insert(basicTemplate) {
            menu {
                menuitems {
                    a(classes = "nav-link", href = Endpoints.HOME.url) {
                        +"Home"
                    }
                }
                menuitems {
                    a(classes = "nav-link", href = Endpoints.LOGOUT.url) {
                        +"Logout"
                    }
                }
            }
            content {
                div(classes = "mt-2") {
                    h2 {
                        +"Welcome to the \"Bookstore\""
                    }
                    p {
                        insert(greeting)
                    }
                }
                form(
                    method = FormMethod.post,
                    encType = FormEncType.multipartFormData,
                    action = Endpoints.DOLOGIN.url
                ) {
                    div(classes = "mb-3") {
                        input(type = InputType.text, classes = "form-control", name = "username") {
                            placeholder = "Type in your username here..."
                            attributes["aria-label"] = "Username"
                            attributes["aria-describedby"] = "basic-addon1"
                        }
                    }
                    div(classes = "mb-3") {
                        input(type = InputType.password, classes = "form-control", name = "password") {
                            placeholder = "Type in your password here..."
                            attributes["aria-label"] = "Password"
                            attributes["aria-describedby"] = "basic-addon1"
                        }
                    }
                    div(classes = "mb-3") {
                        button(classes = "btn btn-primary", type = ButtonType.submit) {
                            +"Login"
                        }
                    }
                }
            }
        }
    }
}