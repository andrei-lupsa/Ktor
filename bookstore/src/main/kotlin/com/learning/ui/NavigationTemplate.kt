package com.learning.ui

import io.ktor.server.html.*
import kotlinx.html.*

class NavigationTemplate : Template<FlowContent> {
    val menuitems = PlaceholderList<UL, FlowContent>()
    override fun FlowContent.apply() {
        div {
            nav(classes = "navbar navbar-expand-md navbar-dark bg-dark") {
                a(classes = "navbar-brand", href = "#") { +"My Bookstore" }
                button(classes = "navbar-toggler", type = ButtonType.button) {
                    attributes["data-toggle"] = "collapse"
                    attributes["data-target"] = "#navbarsExampleDefault"
                    attributes["aria-controls"] = "navbarsExampleDefault"
                    attributes["aria-expanded"] = "false"
                    attributes["aria-label"] = "Toggle navigation"
                    span(classes = "navbar-toggler-icon")
                }
                div(classes = "collapse navbar-collapse") {
                    id = "navbarsExampleDefault"
                    ul(classes = "navbar-nav mr-auto") {
                        each(menuitems) {
                            li {
                                insert(it)
                            }
                        }
                    }
                }
            }
        }
    }
}