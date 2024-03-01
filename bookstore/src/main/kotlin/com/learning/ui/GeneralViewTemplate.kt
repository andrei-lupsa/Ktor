package com.learning.ui

import com.learning.data.Cart
import io.ktor.server.html.*
import kotlinx.html.*

class GeneralViewTemplate(private val cart: Cart? = null) : Template<HTML> {
    val content = Placeholder<HtmlBlockTag>()

    override fun HTML.apply() {
        head {
            link(
                rel = "stylesheet",
                href = "https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css",
                type = "text/css"
            ) {
                integrity = "sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
                attributes["crossorigin"] = "anonymous"
            }
        }

        body {
            insert(NavigationTemplate(cart)) {
                menuitems {
                    a(classes = "nav-link", href = Endpoints.HOME.url) { +"Home" }
                }
                if (cart == null) {
                    menuitems {
                        a(classes = "nav-link", href = Endpoints.LOGIN.url) { +"Login" }
                    }
                } else {
                    menuitems {
                        a(classes = "nav-link", href = Endpoints.LOGOUT.url) { +"Logout" }
                    }
                    menuitems {
                        a(classes = "nav-link", href = Endpoints.BOOKS.url) { +"Books" }
                    }
                }
            }

            div(classes = "container") {
                div(classes = "row") {
                    div(classes = "col-md-12") {
                        insert(content)
                    }
                }
            }
            script(
                type = "javascript",
                src = "https://code.jquery.com/jquery-3.5.1.slim.min.js"
            ) {
                integrity = "sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
                attributes["crossorigin"] = "anonymous"
            }
            script(
                type = "javascript",
                src = "https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
            ) {
                integrity = "sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
                attributes["crossorigin"] = "anonymous"
            }
            script(
                type = "javascript",
                src = "https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"
            ) {
                integrity = "sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s"
                attributes["crossorigin"] = "anonymous"
            }
        }
    }
}